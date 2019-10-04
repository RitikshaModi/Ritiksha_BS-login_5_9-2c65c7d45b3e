package com.example.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository mRepository;
    private LiveData<List<LoginMain>> mgetdetails;

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new LoginRepository(application);
        mgetdetails = mRepository.getdetails();
    }

    LiveData<List<LoginMain>> getuserdetail(){return mgetdetails;}

    //public void insert(String first_name, String last_name, String email, String password){
      //  mRepository.insert(first_name,last_name,email,password);
    //}

    public void insert(LoginMain data){
        Log.d("insert","LoginviewModel");
        mRepository.insert(data);
    }

    boolean checkValidLogin(String username, String password){
        return mRepository.isValidAccount(username,password);
    }

    String firstname(String info) throws ExecutionException, InterruptedException {
        return mRepository.getfirstname(info);
    }

    String lastname(String info) throws ExecutionException, InterruptedException {
        return mRepository.getlastname(info);
    }
    public void update(String password,String email){
        Log.d("password","start");
        mRepository.update(password,email);
        Log.d("password","viewmodel");
    }

    public void updateprofile(String f_name, String l_name, String new_email, String email){
        mRepository.updateprofile(f_name,l_name,new_email,email);
    }
}
