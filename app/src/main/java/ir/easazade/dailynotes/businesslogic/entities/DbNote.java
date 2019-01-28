package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmObject;

import java.sql.Timestamp;

public class DbNote extends RealmObject {

    public String uuid;
    public String userId;
    public String title;
    public String content;
    public Timestamp createdAt;
    public int color;

    public DbNote(String uuid, String userId, String title, String content, Timestamp createdAt, int color) {
        this.uuid = uuid;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.color = color;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
