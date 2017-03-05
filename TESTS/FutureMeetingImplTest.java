/**
 * Created by Damanjit on 26/02/2017.
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FutureMeetingImplTest {

		private Calendar date = new GregorianCalendar();
		private Set<Contact> contacts = new LinkedHashSet<>();
		private Meeting meeting;

		@Before
		public void testConstructor() {
				date.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				meeting = new FutureMeetingImpl(1234,date,this.contacts);
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
		public void testNames(){
				contacts = meeting.getContacts();
				int i=1;
				for (Contact contact:contacts) {
						assertEquals("Names don't match", contact.getName(), "A"+i);
						i++;
				}
		}
}
