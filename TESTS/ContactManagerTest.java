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

	@Test
	public void newContactTest(){
		manager.addNewContact("A","noteA");
		manager.addNewContact("B","noteB");
		manager.addNewContact("C","noteC");
		boolean exception = false;
		try{
			manager.addNewContact("D","");
		} catch (IllegalArgumentException e){
			exception=true;
		}
		assertTrue("Empty Notes - Exception expected", exception);

		exception = false;
		try{
			manager.addNewContact("","Notes");
		} catch (IllegalArgumentException e){
			exception=true;
		}
		assertTrue("Empty Name - Exception expected", exception);

		exception = false;
		try{
			manager.addNewContact("D", null);
		} catch (IllegalArgumentException e){
			exception=true;
		}
		assertTrue("null Date - Exception expected", exception);

	}

}
