package test;
/**
 * Created by Damanjit on 26/02/2017.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;
import impl.ContactImpl;
import impl.PastMeetingImpl;
import spec.Contact;
import spec.Meeting;
import impl.MeetingImplMock;
import spec.PastMeeting;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PastMeetingImplTest {

		private Calendar date = new GregorianCalendar();
		private Set<Contact> contacts = new LinkedHashSet<>();
		private Meeting meeting;
		private String notes = "Meeting notes";

		@Before
		public void testConstructor() {
				date.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				meeting = new PastMeetingImpl(1234,date,this.contacts,notes);
		}

		@Test
		public void testIDs(){
				contacts = meeting.getContacts();
				int i=1;
				for (Contact contact:contacts) {
						assertEquals("IDs don't match", contact.getId(), i);
						i++;
				}
		}

		@Test
		public void testNotes(){
				String notes = ((PastMeeting)meeting).getNotes();
				assertEquals("Notes don't match", this.notes, notes);
		}

		@Test
		public void testNames(){
				contacts = meeting.getContacts();
				int i=1;
				for (Contact contact:contacts) {
						assertEquals("Names don't match", contact.getName(), "A"+i);
						i++;
				}
		}

		@Test
		public void corruptingData() {
				date.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				boolean thrown = false;
				try{
						meeting = new MeetingImplMock(1234,null,this.contacts );
				} catch (NullPointerException e) {
							thrown=true;
				}
				assertTrue("NullPointerException expected with NULL Date Object ",thrown);

				try{
						thrown = false;
						meeting = new MeetingImplMock(1234,date,null );
				} catch (IllegalArgumentException e){
						thrown = true;
				}

                 assertTrue("IllegalArgumentException expected with NULL Contact Object ",thrown);

				try{
						thrown = false;
						meeting = new MeetingImplMock(1234,date,new LinkedHashSet<Contact>() );
				} catch (IllegalArgumentException e){
						thrown = true;
				}

                 assertTrue("IllegalArgumentException expected with EMPTY Contact Object ",thrown);

				try{
						thrown = false;
						meeting = new MeetingImplMock(-1234,date,this.contacts );
				} catch (IllegalArgumentException e){
						thrown = true;
				}
				assertTrue("IllegalArgumentException expected with -Ve ID ",thrown);

				try{
						thrown = false;
						meeting = new MeetingImplMock(0,date,this.contacts );
				} catch (IllegalArgumentException e){
						thrown = true;
				}
				assertTrue("IllegalArgumentException expected with ZERO ID ",thrown);
		}

}
