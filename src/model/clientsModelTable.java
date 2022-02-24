package model;

/**
 * @author Fabian-Bob Ioan George
 * basic class with constructor, setters and getters
 * nothing fancy they store Strings taken from the database or from the GUI
 */

public class clientsModelTable {
    String id, first_name, last_name;

    public clientsModelTable(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
