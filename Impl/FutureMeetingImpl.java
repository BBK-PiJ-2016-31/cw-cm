/**
 * Created by Damanjit on 26/02/2017.
 */

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    /** Creates a new past meeting.
     * @throws IllegalArgumentException if the contact set is empty or a negative parameter ID
     * @throws NullPointerException if the user sends null Date
     */
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> setContacts) {
        super(id, date, setContacts);
    }
}
