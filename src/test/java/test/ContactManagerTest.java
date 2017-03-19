/**
 * Created by Damanjit on 09/03/2017.
 */

package test;

import impl.PastMeetingImpl;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import impl.ContactImpl;
import impl.ContactManagerImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Ignore;
import spec.Contact;
import spec.ContactManager;
import spec.Meeting;
import spec.PastMeeting;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactManagerTest {

	private ContactManager manager = new ContactManagerImpl();
	private int [] ids  = new int[12]; 		// Contact IDs
	private String[] str = new String[12]; 	// for storing names
	private int id1, id2, id3, id4; 		// For meeting IDs
	private Calendar now;					// To get and store dates
    private Set<Contact> contactSet1;		// Set of contacts 1
    private Set<Contact> contactSet2;		// Set of contacts 2
    private Set<Contact> contactSet3;		// Set of contacts 3
    private Set<Contact> contactSet4;		// Set of contacts 4
    private Set<Contact> contactSet = new LinkedHashSet<>();
    private List <Contact> contactList;		// To get and store List of contacts
    private boolean exception;              // To check exception if happened

	@Before
	public void setup(){
		str[0] = "Peter Jones";
		str[1] = "Caroline Henderson";
		str[2] = "Austin Liverpool";

		str[3] = "Rose Matter";
		str[4] = "Alison Coulson";
		str[5] = "Sarah Penfound";

		str[6] = "Michael Leeks";
		str[7] = "Andrew Wheeler";
		str[8] = "Maria Carie";

		str[9] = "Brittany Spears";
		str[10] = "Kelly Jones";
		str[11] = "Simon Haskell";

		ids[0] = manager.addNewContact(str[0],"notePeter");
		ids[1] = manager.addNewContact(str[1],"noteCaroline");
		ids[2] = manager.addNewContact(str[2],"noteAustin");

		ids[3] = manager.addNewContact(str[3],"noteRose");
		ids[4] = manager.addNewContact(str[4],"noteAlison");
		ids[5] = manager.addNewContact(str[5],"noteSarah");

		ids[6] = manager.addNewContact(str[6],"noteMichael");
		ids[7] = manager.addNewContact(str[7],"noteAndrew");
		ids[8] = manager.addNewContact(str[8],"noteMaria");

		ids[9] = manager.addNewContact(str[9],"noteBrittany");
		ids[10] = manager.addNewContact(str[10],"noteKelly");
		ids[11] = manager.addNewContact(str[11],"noteSimon");

		now = Calendar.getInstance();
		contactSet1 = manager.getContacts(new int[]{ids[0], ids[1], ids[2]});
		assertNotNull(contactSet1);
		contactSet2 = manager.getContacts(new int[]{ids[3],ids[4],ids[5]});
        assertNotNull(contactSet2);
		contactSet3 = manager.getContacts(new int[]{ids[6],ids[7],ids[8]});
        assertNotNull(contactSet3);
		contactSet4 = manager.getContacts(new int[]{ids[9],ids[10],ids[11]});
        assertNotNull(contactSet4);

        contactSet.addAll(contactSet1);
        contactSet.addAll(contactSet2);
        contactSet.addAll(contactSet3);
        contactSet.addAll(contactSet4);

        contactList = new ArrayList<>(contactSet);

		now = Calendar.getInstance();
		now.add(Calendar.SECOND,+1); // for a second
		id1 = manager.addFutureMeeting(contactSet1,now);

		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+1);
		id2 = manager.addFutureMeeting(contactSet2,now);

		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+2);
		id3 = manager.addFutureMeeting(contactSet3,now);

		now = Calendar.getInstance();
		now.add(Calendar.MINUTE,+3);
		id4 = manager.addFutureMeeting(contactSet4,now);
	}

	@Test
	public void newContactNotesNameEMPTYTest() {
        exception = false;
        try {
            manager.addNewContact("DJ", "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        exception = false;
        try {
            manager.addNewContact("", "Notes");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void newContactNotesNameNULLTest (){
        exception = false;
        try{
            manager.addNewContact("D", null);
        } catch (NullPointerException e){
            exception=true;
        }
        assertTrue(exception);
        exception = false;
        try{
            manager.addNewContact(null, "Notes");
        } catch (NullPointerException e){
            exception=true;
        }
        assertTrue(exception);
    }

	@Test
	public void getContactsStringNULLTest() {
        exception = false;
        try {
            manager.getContacts((String) null);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void getContactsStringGETALLTest() {
        exception = false;
        Set<Contact> setOfContacts = manager.getContacts("");
        assertEquals(setOfContacts.size(), 12);
        for (Contact c: setOfContacts){
            assertTrue(contactList.contains(c));
        }
    }

    @Test
     public void getContactsStringTest(){
        Set<Contact> setOfContacts = manager.getContacts("e");
        assertEquals(setOfContacts.size(), 11); //Only Alison Coulson removed
        for (Contact c: setOfContacts){
            assertTrue(contactList.contains(c));
        }
        setOfContacts = manager.getContacts("z");
        assertEquals(setOfContacts.size(),0);
        setOfContacts = manager.getContacts("Leeks");
        assertEquals(setOfContacts.size(),1);
        for (Contact c: setOfContacts){
            assertTrue(contactList.contains(c));
        }
	}

	@Test
	public void getContactsIntNULLorWRONG_IDTest() {
        try {
            exception = false;
            manager.getContacts((int[]) null); // Null ID
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        try {
            exception = false;
            manager.getContacts(new int[]{1}); // Wrong ID
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
     public void getContactsIntTest(){
		Set<Contact> setOfContacts = manager.getContacts(ids);
		assertEquals(setOfContacts.size(),12); // All contacts
        for (Contact c: setOfContacts){
            assertTrue(contactList.contains(c));
        }
        setOfContacts = manager.getContacts(new int[] {ids[0],ids[1]});
        assertEquals(setOfContacts.size(),2); // Contacts 0 and 1
        assertTrue(setOfContacts.contains(contactList.get(0)));
        assertTrue(setOfContacts.contains(contactList.get(1)));
    }

	@Test
	public void addFutureMeetingPASTTIMEandNULLTest() {
        now = Calendar.getInstance();
        now.add(Calendar.HOUR, -1);
        try {
            exception = false;
            manager.addFutureMeeting(contactSet1, now); //Meeting in the past
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        now.add(Calendar.YEAR, +1);
        try {
            exception = false;
            manager.addFutureMeeting(contactSet1, null);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);
        try {
            exception = false;
            manager.addFutureMeeting(null, now);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
     public void addFutureMeetingUnknownCONTACTTest() {
        Contact extra = new ContactImpl(12345, "test");
        try {    //Add a contact not in the contactList
            exception = false;
            contactSet1.add(extra);
            manager.addFutureMeeting(contactSet1, now);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        contactSet1.remove(extra); // remove extra contact
    }

    @Test
    public void addFutureMeetingTest(){
         // Test conducted in @Before
        assertTrue(id1>0);
        assertTrue(id2>0);
        assertTrue(id3>0);
        assertTrue(id4>0);
	}

	@Test
	public void getFutureMeetingNULLCheck() {
        Meeting m = manager.getFutureMeeting(1234);
        assertNull(m);
    }

    @Test
    public void getFutureMeetingPASTIDCheck() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        try {
		    exception = false;
		    manager.getFutureMeeting(id1);
        } catch (IllegalStateException e){
		    exception = true;
        }
        assertTrue(exception);
	}

	@Test
    public void getFutureMeetingCheck(){
        Meeting m = manager.getFutureMeeting(id2);
        assertEquals(m.getContacts().size(),3);
        for (Contact c : m.getContacts()){
            assertTrue(contactList.contains(c));
            if (c.getName().equals(str[3]) || c.getName().equals(str[4]) || c.getName().equals(str[5])){
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }

	@Test
	public void getMeetingCheck(){
		assertEquals(null, manager.getMeeting(123));
		assertNotEquals(null, manager.getMeeting(id1));
		assertEquals(manager.getMeeting(id1).getContacts().size(), 3);
		assertNotEquals(null, manager.getMeeting(id2));
        assertEquals(manager.getMeeting(id2).getContacts().size(), 3);
		assertNotEquals(null, manager.getMeeting(id3));
        assertEquals(manager.getMeeting(id3).getContacts().size(), 3);
        assertNotEquals(null, manager.getMeeting(id4));
        assertEquals(manager.getMeeting(id4).getContacts().size(), 3);
    }

	@Test
	public void getFutureMeetingListCheck(){
		try{	// Meeting 1 with ID1 should goto past
			Thread.sleep(1000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		List<Meeting> meetingsList = manager.getFutureMeetingList(contactList.get(0));
		assertTrue(meetingsList.isEmpty()); // Because meeting went in past

        meetingsList = manager.getFutureMeetingList(contactList.get(3));
        assertFalse(meetingsList.isEmpty());
        assertEquals(1,meetingsList.size());
        assertEquals(3, meetingsList.get(0).getContacts().size());
		boolean gotContact=false;
		for (Meeting c:meetingsList){
			Set<Contact> contact = c.getContacts();
			for (Contact con: contact){
				gotContact = (con == contactList.get(3)) ? true : false ;
				if (gotContact) break;
			}
			assertTrue(gotContact);
			gotContact = false;
		}
	}

	@Test
    public void getFutureMeetingListContactNULLandNOTExistTest() {
	    try {
	        exception = false;
	        manager.getFutureMeetingList(null);
        } catch (NullPointerException e) {
	        exception = true;
        }
        assertTrue(exception);

        try {
            exception = false;
            manager.getFutureMeetingList(new ContactImpl(12345678, "DJ"));
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }


	@Test
	public void getMeetingListOnChronologicalCheck() {
        now = Calendar.getInstance();
        List<Meeting> listReceived = manager.getMeetingListOn(now);
        assertEquals(listReceived.size(), 4);
            //Chronologically checking
        assertEquals(listReceived.get(0).getId(), id1);
        assertEquals(listReceived.get(1).getId(), id2);
        assertEquals(listReceived.get(2).getId(), id3);
        assertEquals(listReceived.get(3).getId(), id4);
    }

    @Test
     public void getMeetingListCheck(){
        now = Calendar.getInstance();
        now.add(Calendar.YEAR,+1);
	    List<Meeting> listReceived = manager.getMeetingListOn(now);
        assertTrue(listReceived.isEmpty()); // No meeting after 1 year added YET

        manager.addFutureMeeting(contactSet1,now); // After 1 year
        listReceived = manager.getMeetingListOn(now);
        assertEquals(listReceived.size(),1);

        try {
            exception = false;
            manager.getFutureMeetingList(null);
        } catch (NullPointerException e){
            exception = true;
        }
        assertTrue(exception);

		boolean matchDate=false;
		for (Meeting m:listReceived){
			matchDate=false;
			if (m.getDate().get(Calendar.YEAR)== now.get(Calendar.YEAR)){
				if (m.getDate().get(Calendar.DAY_OF_YEAR)== now.get(Calendar.DAY_OF_YEAR)){
					matchDate=true;
				}
			}
			assertTrue(matchDate);
		}
	}

	@Test
	public void addNewPastMeetingNULL_CONTACT_DATECheck() {
        try {    // Try adding MEETING in future
            exception = false;
            now = Calendar.getInstance();
            now.add(Calendar.HOUR, +1);
            manager.addNewPastMeeting(contactSet4, now, "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        Contact extra = null;
        try {    //Add a contact not in the contactList
            now = Calendar.getInstance();
            now.add(Calendar.YEAR, -1);
            extra = new ContactImpl(12345, "test");
            contactSet4.add(extra);
            manager.addNewPastMeeting(contactSet4, now, "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        contactSet4.remove(extra); // remove extra contact

        try {
            now = Calendar.getInstance();
            now.add(Calendar.YEAR, -1);
            exception = false;
            manager.addNewPastMeeting(new LinkedHashSet<Contact>(), now, "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        try {
            exception = false;
            manager.addNewPastMeeting(contactSet1, now, null);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
     public void addNewPastMeetingTest() {
        now = Calendar.getInstance();
        now.add(Calendar.SECOND,-2);
        int returnedID = manager.addNewPastMeeting(contactSet1,now,"");
        assertTrue(0<returnedID);
    }

    @Test
    public void addMeetingNotesTest(){
	    // Get a meeting ID for a known past Meeting
        try{ // So ID1 becomes a past meeting
            Thread.sleep(1100);
        } catch (Exception e){
            e.printStackTrace();
        }
        // Check the existing notes
        manager.addMeetingNotes(id1,"Test Notes: ");
        assertEquals("Test Notes: ", ((PastMeetingImpl)manager.getMeeting(id1)).getNotes());
        // Add notes to the ID
        manager.addMeetingNotes(id1, "More Notes:");
        // Check the new notes
        assertEquals("Test Notes: More Notes:", ((PastMeetingImpl)manager.getMeeting(id1)).getNotes());
        manager.addMeetingNotes(id1, "More Notes:");
        assertEquals("Test Notes: More Notes:More Notes:", ((PastMeetingImpl)manager.getMeeting(id1)).getNotes());

        // Add a past meeting manually
        now = Calendar.getInstance();
        now.add(Calendar.SECOND,-2);
        int tempId = manager.addNewPastMeeting(contactSet2,now,"Test Notes: ");
        assertEquals("Test Notes: ",((PastMeetingImpl)manager.getMeeting(tempId)).getNotes());
        manager.addMeetingNotes(tempId, "More Notes:");
        assertEquals("Test Notes: More Notes:", ((PastMeetingImpl)manager.getMeeting(tempId)).getNotes());
        manager.addMeetingNotes(tempId, "More Notes:");
        assertEquals("Test Notes: More Notes:More Notes:", ((PastMeetingImpl)manager.getMeeting(tempId)).getNotes());
    }

    @Test
    public void addMeetingNotesNullContactFutureMeetingTest(){
        try{ // Future meeting test
            exception = false;
            manager.addMeetingNotes(id2, "Test");
        } catch (IllegalStateException e) {
            exception = true;
        }
        assertTrue(exception);

        try{ // Null notes test
            Thread.sleep(1100);
            exception = false;
            manager.addMeetingNotes(id1, null);
        } catch (InterruptedException j) {
            j.printStackTrace();
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        try{ // Meeting doesn't exist
            exception = false;
            manager.addMeetingNotes(1, "Test");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void getPastMeetingCheck(){
        try{
            Thread.sleep(1100);
        } catch (Exception e){
            e.printStackTrace();
        }
        PastMeeting meet = manager.getPastMeeting(id1);
        assertEquals(meet.getId(), id1);

        PastMeeting meet1 = manager.getPastMeeting(id1);
        assertEquals(meet1.getId(), id1);
    }

    @Test
    public void getPastMeetingNULLandFUTURECheck(){
        PastMeeting m = manager.getPastMeeting(1);
        assertNull(m);

        try{ // Future meeting
            exception = false;
            manager.getPastMeeting(id2);
        } catch (IllegalStateException e){
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void getPastMeetingListForCheck() {
        try { // so ID1 becomes old meeting
            Thread.sleep(1050);
        } catch (Exception e) {
            e.printStackTrace();
        }
        now = Calendar.getInstance();
        now.add(Calendar.SECOND,-2);
        int returnedID = manager.addNewPastMeeting(contactSet1,now,"");
        assertTrue(0<returnedID);

        // Two meetings should be there
        List<PastMeeting> meeting = manager.getPastMeetingListFor(contactList.get(0));
        assertEquals(2,meeting.size());

        returnedID = manager.addNewPastMeeting(contactSet2,now,"");
        assertTrue(0<returnedID);
        meeting = manager.getPastMeetingListFor(contactList.get(3));
        assertEquals(meeting.size(), 1); // 2nd Set will have 3rd contact

        try { // Unknown contact.
            exception = false;
            manager.getPastMeetingListFor(new ContactImpl(1236575, "DJ", "n"));
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void flushTest(){
        manager.flush();
        ContactManager newManager = new ContactManagerImpl();
        FileInputStream fis = null;
        ObjectInputStream oin = null;
        try {
            fis = new FileInputStream("CMdata.ser");
            oin = new ObjectInputStream(fis);
            newManager = (ContactManagerImpl) oin.readObject();
            oin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Set<Contact> contacts = newManager.getContacts("");
        assertEquals(12, contacts.size());

        contacts = newManager.getContacts("e");
        assertEquals(11,contacts.size());
        //File with final comment
    }
}