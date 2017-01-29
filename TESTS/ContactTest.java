/**
 * Created by D on 28/01/2017.
 */
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactTest {

    @BeforeClass



    @Test
    public void nameTest(){
        Contact trial1 = new ContactImpl(2222, "John", "Boss");
        Contact trial2 = new ContactImpl(1111, "Mike");
        assertEquals("Names Don't Match", trial1.getName(),"John" );
        assertEquals("Names Don't Match", trial2.getName(),"Mike" );
    }
    @Test
    public void idTest(){
        Contact trial1 = new ContactImpl(2222, "John", "Boss");
        Contact trial2 = new ContactImpl(1111, "Mike");
        assertEquals("Names Don't Match", trial1.getName(),"John" );
        assertEquals("Names Don't Match", trial2.getName(),"Mike" );
    }
    @Test
    public void notesTest(){
        Contact trial1 = new ContactImpl(2222, "John", "Boss");
        Contact trial2 = new ContactImpl(1111, "Mike");
        assertEquals("Names Don't Match", trial1.getName(),"John" );
        assertEquals("Names Don't Match", trial2.getName(),"Mike" );
    }
}
