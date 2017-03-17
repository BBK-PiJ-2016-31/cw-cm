package impl;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;

/**
 * Created by Damanjit on 09/03/2017.
 */

public class ContactManagerImpl implements ContactManager, Serializable {
    private IdGenerator id = new IdGenerator();
    private List<Meeting> meetingsList = new LinkedList<>();
    private List<Contact> contacts = new LinkedList<>();
    Calendar now;
    FileOutputStream fos = null;
    ObjectOutputStream ous = null;
    String filename = "CMdata.txt";


    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException, NullPointerException {
        // Null input check
        if (date == null || contacts == null) {
            throw new NullPointerException();
        }
        // Unknown contact check
        if (unknownContactCheck(contacts)){
            throw new IllegalArgumentException();
        }

        now = Calendar.getInstance();
        // Valid future date check
        if (checkDate(date) == -1) {
            throw new IllegalArgumentException();
        }
        int newId = id.getMeetingId();
        meetingsList.add(new FutureMeetingImpl(newId,date,contacts));
        return newId;
    }

    /**
     * Method checks is supplied contacts exist.
     * @param contacts - Set input of contacts
     * @return - returns false if any one of the supplied contact is not in the list
     */
    private boolean unknownContactCheck(Set<Contact> contacts){
        boolean match = false;
        for (Contact c: contacts) {
            match = inTheList(c);
            if (!match) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check the given date.
     * @param date - Date to be checked.
     * @return return 1, -1, 0 based on date in the future, past or NULL.
     */
    private int checkDate(Calendar date){
        now = Calendar.getInstance();
        if (date.getTime().after(now.getTime())){
            return 1;
        } else if (date.getTime().before(now.getTime())){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        sortMeetings();
        Meeting m = getMeeting(id);
        if (m !=null) {
            if (m instanceof FutureMeetingImpl) {
                throw new IllegalStateException();
            }
            return (PastMeeting) m;
        }
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) throws IllegalStateException {
        sortMeetings();
        Meeting c = getMeeting(id);
        if (c !=null) {  // Found meeting
            if (c instanceof PastMeetingImpl) {
                throw new IllegalStateException();
            }
            return (FutureMeeting) c;
        }
        return null;
    }

    @Override
    public Meeting getMeeting(int id) {
        Meeting r = null;
        for (Meeting c:meetingsList) {
            if (c.getId() == id) {  // Found meeting
                r = c;
                break;
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
        now = Calendar.getInstance();
        List<Meeting> futureMeetings = new ArrayList<>();
        for (Meeting e: meetingsList) {
            Set<Contact> receivedContacts = e.getContacts();
            for (Contact d: receivedContacts) {
                if (equalsCheck(contact,d)) {
                    if (checkDate(e.getDate()) == 1) {
                        futureMeetings.add(e);
                    }
                    break;
                }
            }
        }
        if (futureMeetings.size()<=1){
            return futureMeetings;
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
    private boolean equalsCheck(Contact a, Contact b) {
        return ((a.getId() == b.getId()) && (a.getName().equals(b.getName())));
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

    /** Method to convert FutureMeetings to PastMeetings.
     *
     */
    private void sortMeetings(){
        Calendar now = Calendar.getInstance();
        ListIterator<Meeting> meetingIterator = meetingsList.listIterator();
        while (meetingIterator.hasNext()){
            Meeting m = meetingIterator.next();
            if (m.getDate().getTime().before(now.getTime())) {
                Calendar tempDate = m.getDate();
                int tempID = m.getId();
                Set<Contact> tempContact = m.getContacts();
                if (m instanceof FutureMeetingImpl) {
                    meetingIterator.set(new PastMeetingImpl(tempID,tempDate,tempContact,"" ));
                }
            }
        }
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) throws NullPointerException, IllegalArgumentException {
        if (contact == null) {
            throw new NullPointerException();
        }
        if (!inTheList(contact)) {
            throw new IllegalArgumentException();
        }
        sortMeetings();

        List<PastMeeting> newList = new ArrayList<>();
        for (Meeting m: meetingsList) {
            if (m.getContacts().contains(contact)) {
                if (m instanceof PastMeetingImpl) {
                    newList.add((PastMeeting) m);
                }
            }
        }
        return (newList.isEmpty() ? null : newList);
    }

    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws IllegalArgumentException, NullPointerException {
        // Null input check
        if (date == null || contacts == null || text == null) {
            throw new NullPointerException();
        }
        // Check for contact size & date validity
        if (contacts.isEmpty() || checkDate(date) == 1) {
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
    public PastMeeting addMeetingNotes(int id, String text) throws IllegalArgumentException, IllegalStateException, NullPointerException, ClassCastException {
        if (text == null) {
            throw new NullPointerException();
        }
        sortMeetings();
        Meeting m = getMeeting(id);
        if (m!=null) {
            if (m instanceof FutureMeetingImpl){
                throw new IllegalStateException();
            } else {
                ((PastMeetingImpl) m).addNotes(text);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return (PastMeeting)m;
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
        contacts.add(new ContactImpl(newId,name,notes));
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
    public Set<Contact> getContacts(int[] ids) throws IllegalArgumentException {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException();
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
        try {
            fos = new FileOutputStream(filename);
            ous = new ObjectOutputStream(fos);
            ous.writeObject(this);
            ous.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}