package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AboutUs extends RealmObject {

    @PrimaryKey
    public int id = 1;
    public String teamName;

    public AboutUs(String teamName) {
        this.teamName = teamName;
    }

    public AboutUs() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
