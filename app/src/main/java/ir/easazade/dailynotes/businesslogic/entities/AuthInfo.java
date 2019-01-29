package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

public class AuthInfo extends RealmObject {

    @PrimaryKey
    public int id = 1;
    public String token;
    public String bearer;
    public Date lastLogin;

    public AuthInfo(String token, String bearer, Date lastLogin) {
        this.token = token;
        this.bearer = bearer;
        this.lastLogin = lastLogin;
    }

    public AuthInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
