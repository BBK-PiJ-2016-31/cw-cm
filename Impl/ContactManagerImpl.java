import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Damanjit on 09/03/2017.
 */

public class ContactManagerImpl implements ContactManager {
    private IdGenerator id = new IdGenerator();
    private List<Meeting> meetingsList = new LinkedList<>();
    private List<Contact> contacts = new LinkedList<>();

    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException, NullPointerException {
        // Null input check
        if (date == null || contacts == null) {
            throw new NullPointerException();
        }
        // Unknown contact check
        boolean allMatch = false;
        for (Contact c: contacts) {
            allMatch = inTheList(c);
            if (!allMatch) {
                throw new IllegalArgumentException();
            }
        }
        Calendar now = Calendar.getInstance();
        // Valid future date check
        if (date.before(now)) {
            throw new IllegalArgumentException();
        }
        int newId = id.getMeetingId();
        Meeting newMeet = new FutureMeetingImpl(newId,date,contacts);
        meetingsList.add(newMeet);
        return newId;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) throws IllegalStateException {
        FutureMeeting r = null;
        for (Meeting c:meetingsList) {
            if (c.getId() == id) {  // Found meeting
                if (c.getDate().after(Calendar.getInstance())) {
                    r = (FutureMeeting) c;
                } else {
                    throw new IllegalStateException();
                }
            }
        }
        return r;
    }

    @Override
    public Meeting getMeeting(int id) {
        Meeting r = null;
        for (Meeting c:meetingsList) {
            if (c.getId() == id) {  // Found meeting
                r = c;
            }
        }
        return r;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException, NullPointerException {
        if (contact == null) {
            throw new NullPointerException();
        }
        if (!inTheList(contact)) {
            throw new IllegalArgumentException();
        }
        Calendar now = Calendar.getInstance();
        List<Meeting> futureMeetings = new ArrayList<>();
        for (Meeting e: meetingsList) {
            Set<Contact> receivedContacts = e.getContacts();
            for (Contact d: receivedContacts) {
                if (equalsCheck(contact,d)) {
                    if (e.getDate().getTime().after(now.getTime())) {
                        futureMeetings.add(e);
                    }
                    break;
                }
            }
        }
        return sortChorologically(futureMeetings);

    }

    /** Sorts a List.
     * Method to sort meeting list in Chronological order
     * @param futureMeetings - list of meeting
     * @return - sorted list
     */
    public List<Meeting> sortChorologically(List<Meeting> futureMeetings) {
        // SORT OUT CHRONOLOGICALLY
        Collections.sort(futureMeetings, new Comparator<Meeting>() {
            public int compare(Meeting m1, Meeting m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
        return futureMeetings;
    }

    @Override
    public List<Meeting> getMeetingListOn(Calendar date) throws NullPointerException {
        if (date == null) {
            throw new NullPointerException();
        }
        List<Meeting> toSend = new ArrayList<>();
        for (Meeting m : meetingsList) {
            if (m.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
                if (m.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) {
                    toSend.add(m);
                }
            }
        }
        if (toSend.size() > 1) {
            return (sortChorologically(toSend));
        }
        return toSend;
    }

    /** Checks two contacts.
     * Method to check if two contacts are the same
     * @param a - First contact object
     * @param b - Second contact object
     * @return - Returns if the two objects are the same
     */
    public boolean equalsCheck(Contact a, Contact b) {
        return ((a.getId() == b.getId()) && a.getName().equals(b.getName()));
    }

    /**
     * Method to check if the given contact is in the existing list
     * @param c - Contact to be checked if is in the List
     * @return - true if its found in the list. False otherwise
     */
    private boolean inTheList(Contact c) {
        for (Contact b:contacts) {
            if (c.getId() == b.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws IllegalArgumentException, NullPointerException {
        Calendar now = Calendar.getInstance();

        // Null input check
        if (date == null || contacts == null || text == null) {
            throw new NullPointerException();
        }
        // Check for contact size & date validity
        if (contacts.size() == 0 || date.getTime().after(now.getTime())) {
            throw new IllegalArgumentException();
        }
        // Unknown contact check
        for (Contact c: contacts) {
            boolean match = inTheList(c);
            if (!match) {
                throw new IllegalArgumentException();
            }
        }
        // Setup the new Past Meeting now
        int newId = id.getMeetingId();
        meetingsList.add(new PastMeetingImpl(newId,date,contacts,text));
        return newId;
    }

    @Override
    public PastMeeting addMeetingNotes(int id, String text) throws IllegalArgumentException, IllegalStateException, NullPointerException {
//        throw new IllegalArgumentException();
        PastMeetingImpl meet = null;
        for (Meeting m : meetingsList){
            if (m.getId() == id){
                Calendar now = Calendar.getInstance();
                if ((m.getDate().getTime()).before(now.getTime())){
                    meet = (PastMeetingImpl)m;
                    meet.addNotes(text);
                    break;
                }
            }
        }
        return meet;
    }

    @Override
    public int addNewContact(String name, String notes)throws IllegalArgumentException, NullPointerException {
        if (name == null || notes == null) {
            throw new NullPointerException();
        }
        if (name.isEmpty() || notes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int newId = id.getContactId();
        Contact contact = new ContactImpl(newId,name,notes);
        contacts.add(contact);
        return newId;
    }

    @Override
    public Set<Contact> getContacts(String name)throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        Set<Contact> contact = new LinkedHashSet<>();
        for (Contact c: contacts) {
            if (c.getName().contains(name)) {
                contact.add(c);
            } else if (name.isEmpty()) {
                contact.add(c);
            }
        }
        return contact;
    }

    @Override
    public Set<Contact> getContacts(int[] ids) throws IllegalArgumentException, NullPointerException {
        if (ids.length == 0) {
            throw new NullPointerException();
        }
        Set<Contact> contact = new LinkedHashSet<>();
        int idLength = ids.length;
        for (Contact c : contacts) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == c.getId()) {
                    contact.add(c);
                    idLength--;
                    if (idLength == 0) {
                        break;
                    }
                }
            }
            if (idLength == 0) {
                break;
            }
        }
        if (idLength > 0) {
            throw new IllegalArgumentException();
        }
        return contact;
    }

    @Override
    public void flush() {

    }
}
