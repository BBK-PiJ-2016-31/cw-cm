package spec;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Damanjit on 09/03/2017.
 */
public class IdGenerator {
    private Set<Integer> meetingId = new TreeSet<>();
    private Set<Integer> contactId = new TreeSet<>();
    private Random numGen = new Random();
    /** Creates a Unique & Random meeting ID.
     * @return ID
     */
    public int getMeetingId() {
        int id = numGen.nextInt(2147483647);
        if (id == 0 || meetingId.contains(id)) {
            getMeetingId();
        } else {
            meetingId.add(id);
        }
        return id;
    }

    /** Creates a Unique & Random Contact ID.
     * @return ID
     */
    public int getContactId() {
        int id = numGen.nextInt(2147483647);
        if (id == 0 || contactId.contains(id)) {
            getContactId();
        } else {
            contactId.add(id);
        }
        return id;
    }
}
