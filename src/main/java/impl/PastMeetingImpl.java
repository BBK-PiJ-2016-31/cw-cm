package impl;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import spec.Contact;
import spec.PastMeeting;

/**
 * Created by Damanjit on 25/02/2017.
 */

public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {

    private String notes;

    public PastMeetingImpl(int id, Calendar date, Set<Contact> setContacts, String notes) {
        super(id, date, setContacts);
        this.notes = notes;
    }

    @Override
    public String getNotes() {
        // Send empty string or existing notes if any
        return ((notes == null) ? null : notes);
    }

    /**
     * Append new notes to existing notes.
     * @param notes Input parameter
     */
    public void addNotes(String notes) {
        this.notes += notes;
    }
}
