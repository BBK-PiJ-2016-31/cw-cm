package impl;

import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Damanjit on 09/03/2017.
 */
public class IdGenerator implements Serializable {
    private Set<Integer> meetingId = new TreeSet<>();
    private Set<Integer> contactId = new TreeSet<>();
    // Store in treeset to they never duplicate

    private Random numGen = new Random();

    /** Creates a Unique & Random meeting ID.
     * @return ID a Unique ID
     */

    public int getMeetingId() {
        //Upper bound to be the MAX value of int
        int id = numGen.nextInt(2147483647);
        // Don't accept a '0' or a duplicate ID
        if (id == 0 || meetingId.contains(id)) {
            getMeetingId(); // Recursion
        } else {
            meetingId.add(id);
        }
        return id;
    }

    /** Creates a Unique & Random Contact ID.
     * @return ID - Unique ID
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
