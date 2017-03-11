import com.sun.tools.javac.util.BasicDiagnosticFormatter.BasicConfiguration.SourcePosition;
import java.util.Calendar;
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
	public List<Meeting> getFutureMeetingList(Contact contact) {
		return null;
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		return null;
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
