package com.example.login;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginRepository {

    private LoginDao mloginDao;
    private LiveData<List<LoginMain>> mlogindetail;

    LoginRepository(Application application){
        LoginRoomDatabase db = LoginRoomDatabase.getDatabase(application);
        mloginDao = db.loginDao();
        mlogindetail = mloginDao.getUserdetail();
    }

    LiveData<List<LoginMain>> getdetails(){ return mlogindetail;}

    public void update(String password, String email){
        Log.d("password_repository",password);
        new updateAsyncTask(mloginDao).execute(password,email);
        Log.d("password_repo_complete",password);
    }

    public void updateprofile(String f_name, String l_name, String new_email, String email){
        new updateprofileAsyncTask(mloginDao).execute(f_name,l_name,new_email,email);
    }

    private static class updateAsyncTask extends AsyncTask<String, Void, Void>{

        private LoginDao mAsyncTaskDao;

        updateAsyncTask(LoginDao dao){ mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("pass_repository_pass",strings[0]);
            Log.d("pass_repository_repo",strings[1]);
            mAsyncTaskDao.updatepassword(strings[0],strings[1]);
            return null;
            //return null;
        }

    }

    private static class updateprofileAsyncTask extends AsyncTask<String, Void, Void>{

        private LoginDao mAsyncTaskDao;

        updateprofileAsyncTask(LoginDao dao){mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(String... strings) {

            mAsyncTaskDao.updateprofile(strings[0],strings[1],strings[2],strings[3]);
            return null;
        }
    }


    public void insert(LoginMain data){
        Log.d("insert","LoginRepository_insert");
        new insertAsyncTask(mloginDao).execute(data);
    }


    private static class insertAsyncTask extends AsyncTask<LoginMain, Void, Void>{

        private LoginDao mAsyncTaskDao;

        insertAsyncTask(LoginDao dao){mAsyncTaskDao = dao;}
        //Log.d("insert","LoginRepository_asynctask");
        @Override
        protected Void doInBackground(final LoginMain... loginMains) {
            //mAsyncTaskDao.insert(loginMains[0],loginMains[1],loginMains[2],loginMains[3]);
            Log.d("insert","LoginRepository_asynctask");
            mAsyncTaskDao.insert(loginMains[0]);
            return null;
        }

    }


    public boolean isValidAccount(String username, final String password)
    {
        //LoginMain userAccount = mloginDao.getAccount(username);
        //return userAccount.getPassword().equals(password);
        String params[] = {username,password};
        AsyncTask result = new isValidAsyncTask(mloginDao).execute(params);
        boolean valid = false;
        try {
            valid = (boolean)result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Valid",""+valid);
        return valid;
    }

    private static class isValidAsyncTask extends AsyncTask<String, Void, Boolean>{

        private LoginDao mAsyncTaskDao;
        isValidAsyncTask(LoginDao dao){mAsyncTaskDao = dao;}

        @Override
        protected Boolean doInBackground(String... loginMains) {
            LoginMain userAccount = mAsyncTaskDao.getAccount(loginMains[0]);
            if(userAccount != null)
                return userAccount.getPassword().equals(loginMains[1]);
            else
                return false;
            // return null;
        }
    }

    public String getfirstname(String info) throws ExecutionException, InterruptedException {
        AsyncTask name = new getfirstnameAsyncTask(mloginDao).execute(info);
        String firstname = (String) name.get();
        return firstname;
    }

    private static class getfirstnameAsyncTask extends AsyncTask<String, Void, String>{

        private LoginDao mAsyncTaskDao;

        getfirstnameAsyncTask(LoginDao dao){mAsyncTaskDao = dao;}

        @Override
        protected String doInBackground(String... loginMains) {
            LoginMain userdetail = mAsyncTaskDao.getAccount(loginMains[0]);
            return userdetail.getUFirstname();

        }
    }

    public String getlastname(String info) throws ExecutionException, InterruptedException {
        AsyncTask name = new getlastnameAsyncTask(mloginDao).execute(info);
        String firstname = (String) name.get();
        return firstname;
    }

    private static class getlastnameAsyncTask extends AsyncTask<String, Void, String>{

        private LoginDao mAsyncTaskDao;

        getlastnameAsyncTask(LoginDao dao){mAsyncTaskDao = dao;}

        @Override
        protected String doInBackground(String... loginMains) {
            LoginMain userdetail = mAsyncTaskDao.getAccount(loginMains[0]);
            return userdetail.getULastname();

        }
    }
}