import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by D on 28/01/2017.
 */

public class ContactImpl implements Contact {
    private String name;
    private String notes;
    private int id;

    public ContactImpl(int id, String name, String notes) {
        this.id=id;
        this.name=name;
        this.notes=notes;
    }

    public ContactImpl(int id, String name) {
        this(id, name, null);
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact.
     */
    @Override
    public int getId(){
        //throw new NotImplementedException();
        return this.id;
    }

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact.
     */
    @Override
    public String getName(){
        //throw new NotImplementedException();
        return this.name;
    }

    /**
     * Returns our notes about the contact, if any.
     *
     * If we have not written anything about the contact, the empty
     * string is returned.
     *
     * @return a string with notes about the contact, maybe empty.
     */
    @Override
    public String getNotes(){
        //throw new NotImplementedException();
        return this.notes;
    }

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    @Override
    public void addNotes(String note){
        throw new NotImplementedException();
    }
}