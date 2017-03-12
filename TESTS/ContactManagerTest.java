/**
 * Created by Damanjit on 09/03/2017.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactManagerTest {

	ContactManagerImpl manager = new ContactManagerImpl();
	int [] ids  = new int[4];
	String[] str = new String[6];
	int id1, id2, id3, id4;
	Calendar now;
	Set<Contact> contactsToSend;
	List<Contact> contactList;

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

		now = Calendar.getInstance();
		contactsToSend = manager.getContacts("");
		contactList = new ArrayList<>(contactsToSend);
		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+9);
		id2 = manager.addFutureMeeting(contactsToSend,now);
		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+4);
		id3 = manager.addFutureMeeting(contactsToSend,now);
		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+6);
		id4 = manager.addFutureMeeting(contactsToSend,now);
		now = Calendar.getInstance();
		now.add(Calendar.SECOND,+1);
		id1 = manager.addFutureMeeting(contactsToSend,now);
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
			manager.getContacts((int[]) null);
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
		Contact extra=null;
		boolean exception = false;
		try{	//Add a contact not in the contactList
			extra = new ContactImpl(12345, "test");
			contactsToSend.add(extra);
			manager.addFutureMeeting(contactsToSend,now);
		} catch (IllegalArgumentException e){
			exception = true;
		}
		assertTrue(exception);
		contactsToSend.remove(extra); // remove extra contact

		exception=false;
		try{	// Try adding time in past
			now.add(Calendar.HOUR, -1);
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(null, manager.getFutureMeeting(123));
		boolean exception = false;
		try{
			manager.getFutureMeeting(id1);
		} catch (IllegalStateException e){
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void getMeetingCheck(){
		assertEquals(null, manager.getMeeting(123));
		assertNotEquals(null, manager.getMeeting(id1));
		assertNotEquals(null, manager.getMeeting(id2));
		assertNotEquals(null, manager.getMeeting(id3));
		assertNotEquals(null, manager.getMeeting(id4));
	}

	@Test
	public void getFutureMeetingListCheck(){
		try{	// 4 Added. One meeting should to to past time in this wait.
			Thread.sleep(1000);
		} catch (Exception e){
			e.printStackTrace();
		}
		List<Meeting> meetingsList = manager.getFutureMeetingList(contactList.get(0));
		assertNotEquals(null, meetingsList);
		assertEquals(3,meetingsList.size());
		boolean gotContact=false;
		for (Meeting c:meetingsList){
			Set<Contact> contact = c.getContacts();
			for (Contact check: contact){
				gotContact= manager.equalsCheck(check,contactList.get(0));
				if (gotContact) break;
			}
			assertTrue(gotContact);
			gotContact = false;
		}
	}

//	@Test
//	public void getMeetingListOnCheck(){
//		Calendar date = Calendar.getInstance();
//		Date date1 = date.getTime();
//		List<Meeting> listReceived = manager.getMeetingListOn(date);
//		boolean match=false;
//		for (Meeting m:listReceived){
//			Date date2 = m.getDate().getTime();
//			match=false;
//			if (date1.compareTo(date2) == 0) match = true;
//			assertTrue(match);
//		}
//	}
}