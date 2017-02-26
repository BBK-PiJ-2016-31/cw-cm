/**
 * Created by Damanjit on 26/02/2017.
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeetingImplTest {

		Calendar date;
		Calendar date1;
		@Test
		public void setMeeting(){
				date = new GregorianCalendar();
				date.set(2017,3,1,9,33);

				date1 = new GregorianCalendar();
				date1.set(2017,3,1,9,33);

				System.out.println(date.compareTo(date1));
//				Set<Contact> contact
//				Meeting meeting = new MeetingImpl(1234,date,contacts ) {
				}
}
