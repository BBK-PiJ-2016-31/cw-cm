/**
 * Created by Damanjit on 09/03/2017.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest {

		private Set<Contact> contacts = new LinkedHashSet<>();
		private Calendar date = new GregorianCalendar();
		private ContactManager manager = new ContactManagerImpl();

		@Before
		public void SetUp(){
				date.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				manager.addFutureMeeting(contacts, date);
		}

		
}
