package com.karolina.check.models;

import java.util.Date;

/**
 * Created by RicardoAndrés on 07/04/2017.
 */

public class User {
    String name;
    String username;
    String password;
    Date birthday;

    public User() {
    }

    public User(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
