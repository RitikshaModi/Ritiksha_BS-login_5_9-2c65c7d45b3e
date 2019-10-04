package com.example.login;

import android.app.Application;

public class LoginApplication extends Application {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private String f_name;

    public String getname(){return f_name;}
    public void setname(String f_name) {this.f_name = f_name;}

}
