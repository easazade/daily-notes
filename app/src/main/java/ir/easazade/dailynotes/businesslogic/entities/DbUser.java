package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DbUser extends RealmObject {

    public String id;
    public String username;
    public String email;
    public RealmList<DbNote> notes;

    public DbUser(String id, String username, String email, RealmList<DbNote> notes) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<DbNote> getNotes() {
        return notes;
    }

    public void setNotes(RealmList<DbNote> notes) {
        this.notes = notes;
    }
}
