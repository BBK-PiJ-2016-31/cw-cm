import java.util.Calendar;
import java.util.Set;

/**
 * A meeting to be held in the future
 * The class implementing this interface must have only one constructor
 * with three parameters: an ID (int), a date, and a set of contacts
 * If any of the references / pointers passed as parameters is
 * null, a NullPointerException must be thrown.
 */
public class FutureMeetingImpl implements FutureMeeting {

    private int id;
    private Calendar date;
    private Set<Contact> contact;

    /**
    * Constructor to create a new future meeting®
    */

    public FutureMeetingImpl(int id, Calendar date, Set<Contact> setContacts) {
        this.id = id;
        this.date = date;
        this.contact = setContacts;
    }

    @Override
    public int getId() {
      return 0;
    }

    @Override
    public Calendar getDate() {
      return date;
    }

    @Override
    public Set<Contact> getContacts() {
      return this.contact;
    }
}
