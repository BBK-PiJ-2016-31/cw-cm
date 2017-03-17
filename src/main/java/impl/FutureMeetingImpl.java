package impl; /**
 * Created by Damanjit on 26/02/2017.
 */

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import spec.Contact;
import spec.FutureMeeting;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

    /** Creates a new past meeting.
     * @throws IllegalArgumentException if the contact set is empty or a negative parameter ID
     * @throws NullPointerException if the user sends null Date
     */
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> setContacts) {
        super(id, date, setContacts);
    }
}
