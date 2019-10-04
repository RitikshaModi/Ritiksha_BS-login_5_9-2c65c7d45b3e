package com.example.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert
    void insert(LoginMain account);
    //void insert(LoginMain firstname, LoginMain lastname, LoginMain email, LoginMain password);

    @Update
    void update(LoginMain... password);

    @Query("SELECT * FROM User_detail")//" WHERE User_detail.Id LIKE :username")
    LiveData<List<LoginMain>> getUserdetail();

    @Query("SELECT * FROM User_detail WHERE User_detail.Email LIKE :username ")
    LoginMain getAccount(String username);

//    @Query("SELECT First_name,Last_name,Email FROM User_detail WHERE User_detail.Email LIKE :username")
//    LoginMain getAccountInfo(String username);

    @Query("update User_detail set Password = :password  Where User_detail.Email LIKE :useremail ")
    void updatepassword(String password,String useremail);

    @Query("update User_detail set First_name = :firstname, Last_name = :lastname, Email = :email  Where User_detail.Email LIKE :useremail")
    void updateprofile(String firstname, String lastname, String email,String useremail);
}
