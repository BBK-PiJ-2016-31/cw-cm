import java.util.Calendar;
import java.util.Set;

/**
 * Created by D on 25/02/2017.
 */
public abstract class MeetingImpl implements Meeting {
    private int id;
    private Calendar date;
    private Set<Contact> contacts;

    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        if (contacts.isEmpty()) throw new IllegalArgumentException();
        if (id <= 0 || date == null) throw new NullPointerException();

        this.id = id;
        this.date = date;
        this.contacts = contacts;
    }

}
