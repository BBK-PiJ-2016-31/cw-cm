/**
 * Created by Damanjit on 09/03/2017.
 */
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactManagerTest {

	ContactManager manager = new ContactManagerImpl();
	int [] ids  = new int[4];
	String[] str = new String[6];

	@Before
	public void setup(){
		str[0] = "AAA";
		str[1] = "BBB";
		str[2] = "CCC";
		str[3] = "AAAAAA";
		str[4] = "AAABBB";
		str[5] = "AAACCC";

		ids[0] = manager.addNewContact(str[0],"note3A");
		ids[1] = manager.addNewContact(str[1],"note3B");
		ids[2] = manager.addNewContact(str[2],"note3C");
		ids[3] = manager.addNewContact(str[3],"note6A");
		manager.addNewContact(str[4],"note6B");
		manager.addNewContact(str[5],"note6C");
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
	public void getContactsStringTest(){
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
//		assertTrue("Set of contacts var should be empty",setOfContacts.isEmpty());
	}

	@Test
	public void getContactsIntTest(){
		boolean exception = false;
		try{
			manager.getContacts((Integer)null);
		} catch (NullPointerException e){
			exception=true;
		}
		assertTrue(exception);
		exception = false;

		Set<Contact> setOfContacts = manager.getContacts(ids);
		assertNotEquals(null, setOfContacts);
		int i=0;
		for (Contact s: setOfContacts){
			assertEquals(s.getId(),ids[i]);
			assertEquals(s.getName(),str[i]);
			++i;
		}
	}

	@Test
	public void addFutureMeetingTest(){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR,+1);
		Set<Contact> contactsToSend = manager.getContacts("");
		Integer returnedID = manager.addFutureMeeting(contactsToSend,now);
		assertNotEquals(null, returnedID);
		boolean exception = false;
		try{	//Add a contact not in the contactList
			Contact extra = new ContactImpl(12345, "test");
			contactsToSend.add(extra);
			manager.addFutureMeeting(contactsToSend,now);
		} catch (IllegalArgumentException e){
			exception = true;
		}
		assertTrue(exception);
		exception=false;
		try{	// Try adding time in past
			now.add(Calendar.MINUTE, -3);
			manager.addFutureMeeting(contactsToSend,now); //Meeting in the past
		} catch (IllegalArgumentException e) {
			exception = true;
		}
		assertTrue(exception);
		exception = false;
		try{	// Try sending NULL date
			manager.addFutureMeeting(contactsToSend,null);
		} catch (NullPointerException e) {
			exception = true;
			boolean exception1 = false;
			try{ // try sending NULL contact
				manager.addFutureMeeting(null, now);
			} catch (NullPointerException d){
				exception1 = true;
			}
			assertTrue(exception1);
		}
		assertTrue(exception);
	}

	@Test
	public void getFutureMeetingCheck(){

	}
}
