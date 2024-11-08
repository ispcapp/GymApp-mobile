package com.ispcapp.gymapp.models;

public class UserCredentials {
    private String useremail;
    private String password;

    public UserCredentials(String username, String password) {
        this.useremail = username;
        this.password = password;
    }

    public String getUserEmail() {
        return useremail;
    }

    public void setUserEmail(String username) {
        this.useremail = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}