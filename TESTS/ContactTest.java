/**
 * Created by D on 28/01/2017.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactTest {

    Contact trial1;
    Contact trial2;

    @Before
    public void setUp()
    {
        try{
            trial1 = new ContactImpl(2222, "John", "Boss");
            trial2 = new ContactImpl(-1111, "Mike");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void nameTest(){
        try{
            assertEquals("Names Don't Match", trial1.getName(),"John" );
            assertEquals("Names Don't Match", trial2.getName(),"Mike" );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void idTest(){
        assertEquals("IDs Don't Match", trial1.getId(), 2222 );
        assertEquals("IDs Don't Match", trial2.getId(),1111 );
    }

    @Test
    public void notesTest(){
        assertEquals("Notes1 Don't Match", trial1.getNotes(),"Boss" );
        assertEquals("Notes2 Don't Match", trial2.getNotes(),null );

        String str1= "This note is added to trial1";
        String str2= "This note is added to trial2";
        trial1.addNotes(str1);
        trial2.addNotes(str2);
        assertEquals("Notes1 Don't Match", "Boss "+str1,"Boss "+str1);
        assertEquals("Notes2 Don't Match", str2,str2);
    }
}