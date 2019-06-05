package com.arpaul.mygate_aritra.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User extends BaseDO {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imagePath = "";
    private String userName = "";
    private String passcode = "";

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", userName='" + userName + '\'' +
                ", passcode=" + passcode +
                '}';
    }
}
