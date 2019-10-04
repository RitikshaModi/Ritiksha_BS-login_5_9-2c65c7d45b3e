package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;

public class ProfileInfo extends AppCompatActivity {

    private EditText txt_profile_fname, txt_profile_lname, txt_profile_email;
    private LoginViewModel loginViewModel;
    private Button btn_updateprofile;
    private static final int resultcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        txt_profile_fname = findViewById(R.id.edt_firstname_profile);
        txt_profile_lname = findViewById(R.id.edt_lastname_profile);
        txt_profile_email = findViewById(R.id.edt_email_profile);
        btn_updateprofile = findViewById(R.id.btn_updateprofile);

        final String email = ((LoginApplication)this.getApplication()).getEmail();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        try {
            displayprofileinfo(email);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btn_updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update_first_name = txt_profile_fname.getText().toString();
                String update_last_name = txt_profile_lname.getText().toString();
                String update_email = txt_profile_email.getText().toString();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                loginViewModel.updateprofile(update_first_name,update_last_name,update_email,email);
                ((LoginApplication)ProfileInfo.this.getApplication()).setEmail(update_email);
                ((LoginApplication)ProfileInfo.this.getApplication()).setname(update_first_name);

                Toast.makeText(getApplicationContext(),"profile update successfully",Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void displayprofileinfo(String email) throws ExecutionException, InterruptedException {
        txt_profile_fname.setText(loginViewModel.firstname(email));
        txt_profile_lname.setText(loginViewModel.lastname(email));
        txt_profile_email.setText(email);
    }

}