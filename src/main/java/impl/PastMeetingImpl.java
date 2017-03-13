package impl;

import java.util.Calendar;
import java.util.Set;
import spec.Contact;
import spec.PastMeeting;

/**
 * Created by Damanjit on 25/02/2017.
 */

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    public PastMeetingImpl(int id, Calendar date, Set<Contact> setContacts, String notes) {
        super(id, date, setContacts);
        this.notes = notes;
    }

    @Override
    public String getNotes() {
        return ((notes == null) ? " " : notes);
    }

    public void addNotes(String notes){
        this.notes += notes;
    }
}
