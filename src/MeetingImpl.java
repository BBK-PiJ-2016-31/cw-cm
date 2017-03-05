import java.util.Calendar;
import java.util.Set;

/**
 * Created by Damanjit on 25/02/2017.
 */
public abstract class MeetingImpl implements Meeting {
    private int id;
    private Calendar date;
    private Set<Contact> contacts;

    /** Creates a meeting.
     * @throws IllegalArgumentException if the contact set is empty
     * @throws NullPointerException if the user sends null Date or a negative parameter ID
     */
    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        if (contacts.isEmpty() || id<=0) {
            throw new IllegalArgumentException();
        }
        if (date == null) {
            throw new NullPointerException();
        }
        this.id = id;
        this.date = date;
        this.contacts = contacts;
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
        return contacts;
    }
}