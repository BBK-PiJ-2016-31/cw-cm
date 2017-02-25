public class ContactImpl implements Contact {

    private String name;
    private String notes;
    private int id;

    /**
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if id is <=0 of the ID of the contact.
     */
    public ContactImpl(int id, String name, String notes) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        if (name == null) {
            throw new NullPointerException();
        }
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public ContactImpl(int id, String name) {
        this(id, name, null);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    @Override
    public void addNotes(String note) {
        this.notes += (" " + note) ;
    }
}