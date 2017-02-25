import java.util.Calendar;
import java.util.Set;

/**
 * Created by D on 25/02/2017.
 * The class implementing this interface must have only one constructor
 * with four parameters: an ID (int), a date, a set of contacts that must
 * be non-empty (otherwise, an IllegalArgumentException must be thrown),
 * and a String containing the notes for the meeting.
 * If any of the references / pointers passed as parameters is null,
 * a NullPointerException must be thrown.
 */

public class PastMeetingImpl implements PastMeeting {

    private int id;
    private Calendar date;
    private Set<Contact> contact;
    private String notes;

    /* Creates a new past meeting
     * @throws IllegalArgumentException if the contact set is empty
     * @throws NullPointerException if the user sends null (notes/Date) or a negative parameter ID
     */
    public PastMeetingImpl(int id, Calendar date, Set<Contact> setContacts, String notes) {
        if (setContacts.isEmpty()) throw new IllegalArgumentException();
        if (id < 0 || date == null || notes == null) throw new NullPointerException();
        this.id = id;
        this.date = date;
        this.contact = setContacts;
        this.notes = notes;
    }

    @Override
    public int getId() {
      return id;
    }

    @Override
    public Calendar getDate() {
      return date;
    }

    @Override
    public Set<Contact> getContacts() {
      return contact;
    }

    @Override
    public String getNotes() {
      return notes;
    }
}
