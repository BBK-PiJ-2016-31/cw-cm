package impl;

import java.io.Serializable;
import spec.Contact;

public class ContactImpl implements Contact, Serializable {

    private String name;
    private String notes;
    private int id;

    /**
     * Main constructor initialising the variables when contact is created.
     * @param id - ID of the contact
     * @param name - Name of the contact
     * @param notes - Initial notes about the contact
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

    /**
     * Secondry constructor when no notes are provided when contact is created.
     * @param id - ID of the contact
     * @param name - Name of the contact
     * Uses the main constructor to initialise the contact.
     */

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