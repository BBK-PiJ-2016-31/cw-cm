/**
 * Created by Damanjit on 26/02/2017.
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingImplTest {

		Calendar date = new GregorianCalendar();
		Calendar date1 = new GregorianCalendar();
		Set<Contact> contacts = new HashSet<>();
		Meeting meeting;

		@Before
		public void createData() {
				date.set(2017,3,1,9,33);
				date1.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
		}

		@Test
		public void setMeeting(){
				meeting = new FutureMeetingImpl(1234,date,this.contacts );
		}

		@Test
		public void printContacts(){
				contacts = meeting.getContacts();
				System.out.println(contacts.isEmpty());
				for (Contact contact:contacts)
						System.out.println(
								"Name: " + contact.getName() + " id: " + contact.getId() + " Notes: " + contact
										.getNotes());
		}
}