import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Damanjit on 09/03/2017.
 */
public class Idgenerator {
    private Set<Integer> meetingId = new TreeSet<>();
    private Set<Integer> contactId = new TreeSet<>();

    /** Creates a Unique & Random meeting ID.
     * @return ID
     */
    public int getMeetingId() {
        int id = (int) (Math.random() * 1000000000);
        if (meetingId.contains(id)) {
            getMeetingId();
        } else {
            meetingId.add(id);
        }
        return id;
    }

    /** Creates a Unique & Random Contact ID.
     *
     * @return ID
     */
    public int getContactId() {
        int id = (int) (Math.random() * 1000000000);
        if (contactId.contains(id)) {
            getContactId();
        } else {
            contactId.add(id);
        }
        return id;
    }
}
