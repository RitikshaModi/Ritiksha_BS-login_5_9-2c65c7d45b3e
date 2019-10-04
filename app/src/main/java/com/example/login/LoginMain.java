package com.example.login;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_detail")
public class LoginMain  {

    //@PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "Id")
    public int Id;

    @NonNull
    @ColumnInfo(name = "First_name")
    public String FirstName;

    @NonNull
    @ColumnInfo(name = "Last_name")
    public String LastName;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Email")
    public String Email;

    @NonNull
    @ColumnInfo(name = "Password")
    public String Password;


    /*public LoginMain(String FirstName, String LastName, String Email, String Password){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
    }

    @Ignore
    public LoginMain(int Id, String FirstName, String LastName, String Email, String Password){

        this.Id=Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;

    }*/

    public int getId(){return Id;}

    public void setId(int Id) { this.Id = Id; }

    public String getUFirstname(){return this.FirstName;}

    public void setUFirstname(String FirstName){this.FirstName = FirstName;}

    public String getULastname(){return this.LastName;}

    public void setULastname(String LastName){this.LastName = LastName;}

    public String getUEmail(){return this.Email;}

    public void setUEmail(String Email){this.Email = Email;}

    public String getPassword(){return this.Password;}

    public void setPassword(String Password){this.Password = Password;}

}
