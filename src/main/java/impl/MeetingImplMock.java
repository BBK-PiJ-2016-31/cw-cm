package impl;

import java.util.Calendar;
import java.util.Set;
import spec.Contact;

/**
 * Created by Damanjit on 05/03/2017.
 */
public class MeetingImplMock extends MeetingImpl {

    /**
    * Creates a meeting.
    * Main constructor to create a meeting object.
    *
    * @param id - Id of the meeting object
    * @param date - Date of the meeting
    * @param contacts - Set of contacts which will take part in the meeting
    * @throws IllegalArgumentException if the contact set is empty or the date <= 0
    * @throws NullPointerException if the user sends null Date
    */
    public MeetingImplMock(int id, Calendar date, Set<Contact> contacts) {
        super(id, date, contacts);
    }
}
