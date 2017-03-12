import com.sun.tools.javac.util.BasicDiagnosticFormatter.BasicConfiguration.SourcePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import sun.util.resources.el.CalendarData_el_CY;

/**
 * Created by Damanjit on 09/03/2017.
 */

public class ContactManagerImpl implements ContactManager {
	private IDGenerator id = new IDGenerator();
	private List<Meeting> meetingsList = new LinkedList<>();
	private List<Contact> contacts = new LinkedList<>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException, NullPointerException {
		// Null input check
		if (date == null || contacts== null) throw new NullPointerException();
		// Unknown contact check
		boolean allMatch = false;
		for (Contact c: contacts){
			for (Contact d: this.contacts){
				allMatch = false;
				if (d.equals(c)){
					allMatch = true;
					break;
				}
			}
			if (!allMatch) break;
		}
		if (!allMatch) throw new IllegalArgumentException();
		Calendar now = Calendar.getInstance();
		int newID;
		// Valid future date check
		if (date.after(now)){
			newID= id.getMeetingID();
			Meeting newMeet = new FutureMeetingImpl(newID,date,contacts);
			meetingsList.add(newMeet);
		} else throw new IllegalArgumentException();
		return newID;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) throws IllegalStateException {
		FutureMeeting r = null;
		for (Meeting c:meetingsList){
			if (c.getId()==id){	// Found meeting
				if (c.getDate().after(Calendar.getInstance())){
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
		for (Meeting c:meetingsList){
			if (c.getId()==id){	// Found meeting
				r = c;
			}
		}
		return r;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException, NullPointerException {
		if (contact==null) throw new NullPointerException();
		if (!inTheList(contact)) throw new IllegalArgumentException();
		Calendar now = Calendar.getInstance();
		List<Meeting> futureMeetings = new ArrayList<>();
		for (Meeting e: meetingsList){
			Set<Contact> receivedContacts = e.getContacts();
			for (Contact d: receivedContacts){
				if (equalsCheck(contact,d)) {
					if (e.getDate().getTime().after(now.getTime())) futureMeetings.add(e);
					break;
				}
			}
		}
		// SORT OUT CHRONOLOGICALLY
		Collections.sort(futureMeetings, new Comparator<Meeting>() {
			public int compare(Meeting m1, Meeting m2) {
				return m1.getDate().compareTo(m2.getDate());
			}
		});
		return futureMeetings;
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		List<Meeting> toSend = new ArrayList<>();
		for (Meeting m : meetingsList){
			if (m.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
				if (m.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) {
					toSend.add(m);
				}
			}
		}
		return toSend;
	}

	/**
	 * Method to check if two contacts are the same
	 * @param A - First contact object
	 * @param B - Second contact object
	 * @return - Returns if the two objects are the same
	 */
	public boolean equalsCheck(Contact A, Contact B){
		return ((A.getId() == B.getId()) && A.getName().equals(B.getName()));
	}

	/**
	 * Method to check if the given contact is in the existing list
	 * @param c - Contact to be checked if is in the List
	 * @return - true if its found in the list. False otherwise
	 */
	private boolean inTheList(Contact c){
		for (Contact b:contacts){
			if (c.getId()==b.getId()){
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
	public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		return 0;
	}

	@Override
	public PastMeeting addMeetingNotes(int id, String text) {
		return null;
	}

	@Override
	public int addNewContact(String name, String notes)throws IllegalArgumentException, NullPointerException{
		if (name.isEmpty() || notes.isEmpty()) throw new IllegalArgumentException();
		if (name == null || notes == null) throw new NullPointerException();
		int newID = id.getContactID();
		Contact contact = new ContactImpl(newID,name,notes);
		contacts.add(contact);
		return newID;
	}

	@Override
	public Set<Contact> getContacts(String name)throws NullPointerException {
		if (name == null) throw new NullPointerException();
		Set<Contact> contact = new LinkedHashSet<>();
		for (Contact c: contacts){
			if (c.getName().contains(name)){
				contact.add(c);
			} else if (name.isEmpty()){
				contact.add(c);
			}
		}
		return contact;
	}

	@Override
	public Set<Contact> getContacts(int[] ids) throws IllegalArgumentException, NullPointerException {
		if (ids.length == 0)
			throw new NullPointerException();
		Set<Contact> contact = new LinkedHashSet<>();
		int idLength = ids.length;
		for (Contact c : contacts) {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] == c.getId()) {
					contact.add(c);
					idLength--;
					if (idLength == 0) break;
				}
			}
			if (idLength == 0) break;
		}
		if (idLength>0) throw new IllegalArgumentException();
		return contact;
	}

	@Override
	public void flush() {
	}
}
