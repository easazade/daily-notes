package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DbUser extends RealmObject {

    @PrimaryKey
    public String uuid;
    public String username;
    public String email;
    public RealmList<DbNote> notes;

    public DbUser(String uuid, String username, String email, RealmList<DbNote> notes) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.notes = notes;
    }

    public DbUser() {
    }

    public String getId() {
        return uuid;
    }

    public void setId(String uuid) {
        this.uuid = uuid;
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
