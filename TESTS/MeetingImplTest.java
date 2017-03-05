/**
 * Created by Damanjit on 26/02/2017.
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingImplTest {

		private Calendar date = new GregorianCalendar();
		private Calendar date1 = new GregorianCalendar();
		private Set<Contact> contacts = new LinkedHashSet<>();
		private Meeting meeting;

		@Before
		public void createData() {
				date.set(2017,3,1,9,33);
				date1.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				meeting = new MeetingImplMock(1234,date,this.contacts );
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

		@Test
		public void corruptingData() {
				date.set(2017,3,1,9,33);
				date1.set(2017,3,1,9,33);
				for (int i = 1; i < 10; i++) {
						contacts.add(new ContactImpl(i,"A"+i, "Demo notes"));
				}
				try{
						System.out.print("1) \t\tInserting null Date - \t\t\t");
						meeting = new MeetingImplMock(1234,null,this.contacts );
				} catch (NullPointerException e){
						System.out.println("1) NullPointerException generated");
				}
				try{
						System.out.print("2) \t\tInserting null Contacts - \t");
						meeting = new MeetingImplMock(1234,date,null );
				} catch (IllegalArgumentException e){
						System.out.println("2) IllegalArgumentException Generated");
				}
				try{
						System.out.print("2.1)\tInserting Empty Contacts - \t");
						meeting = new MeetingImplMock(1234,date,new LinkedHashSet<Contact>() );
				} catch (IllegalArgumentException e){
						System.out.println("2.1) IllegalArgumentException Generated");
				}
				try{
						System.out.print("3) \t\tInserting -Ve ID - \t\t\t\t\t");
						meeting = new MeetingImplMock(-1234,date,this.contacts );
				} catch (IllegalArgumentException e){
						System.out.println("3) IllegalArgumentException Generated");
				}
				try{
						System.out.print("4) \t\tInserting ZERO ID - \t\t\t\t");
						meeting = new MeetingImplMock(0,date,this.contacts );
				} catch (IllegalArgumentException e){
						System.out.println("4) IllegalArgumentException Generated");
				}
		}

}