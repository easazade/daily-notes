package ir.easazade.dailynotes.businesslogic.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

public class DbNote extends RealmObject {

    @PrimaryKey
    public String uuid;
    public String userId;
    public String title;
    public String content;
    public Date createdAt;
    public Date updatedAt;
    public String color;

    public DbNote(String uuid, String userId, String title, String content, Date createdAt,
        Date updatedAt, String color) {
        this.uuid = uuid;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.color = color;
    }

    public DbNote() {

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
