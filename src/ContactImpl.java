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

    @Override
    public int getId(){
        //throw new NotImplementedException();
        return this.id;
    }

    @Override
    public String getName(){
        //throw new NotImplementedException();
        return this.name;
    }

    @Override
    public String getNotes(){
        //throw new NotImplementedException();
        return this.notes;
    }

    @Override
    public void addNotes(String note){
        //throw new NotImplementedException();
        this.notes+=(" "+note);
    }
    /*
     * Generates a unique ID for the contact
     * @return A Unique ID for the newly created contact.
     */
    public int generateID(){

    }
}