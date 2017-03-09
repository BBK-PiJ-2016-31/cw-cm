/**
 * Created by Damanjit on 09/03/2017.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactManagerTest {

	ContactManager manager = new ContactManagerImpl();

	@Before
	public void setup(){
		manager.addNewContact("AAA","note3A");
		manager.addNewContact("BBB","note3B");
		manager.addNewContact("CCC","note3C");
		manager.addNewContact("AAAAAA","note6A");
		manager.addNewContact("BBBBBB","note6B");
		manager.addNewContact("CCCCCC","note6C");
	}

	@Test
	public void newContactTest(){
		boolean exception = false;
		try{
			manager.addNewContact("D","");
		} catch (IllegalArgumentException e){
			exception=true;
		}
		assertTrue("Empty Notes - Exception expected", exception);

		exception = false;
		try{
			manager.addNewContact("D", null);
		} catch (NullPointerException e){
			exception=true;
		}
		assertTrue("null Date - Exception expected", exception);

	}


	@Test
	public void getContactsTest(){
		boolean exception = false;
		try{
			manager.getContacts((String)null);
		} catch (NullPointerException e){
			exception=true;
		}
		assertTrue("Null Exception Expected",exception);
		exception = false;

		Set<Contact> setOfContacts = manager.getContacts("AA");
		Contact t=null;
		for (Contact s: setOfContacts){
			if (s.getName().equals("AAA")){
				exception = true;
				t=s;
			}
		}
		assertTrue(exception);
		setOfContacts.remove(t);
		exception=false;
		for (Contact s: setOfContacts){
			if (s.getName().equals("AAAAAA")){
				exception = true;
				t=s;
			}
		}
		assertTrue(exception);
		setOfContacts.remove(t);
		assertTrue("Set of contacts var should be empty",setOfContacts.isEmpty());
	}

}
