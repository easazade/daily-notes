package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AuthInfo extends RealmObject {

    @PrimaryKey
    public int id = 1;
    public String token;
    public String bearer;
    public String lastLogin;

    public AuthInfo(String token, String bearer, String lastLogin) {
        this.token = token;
        this.bearer = bearer;
        this.lastLogin = lastLogin;
    }

    public AuthInfo() {
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

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
