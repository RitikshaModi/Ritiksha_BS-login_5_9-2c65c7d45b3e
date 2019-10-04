package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private LoginViewModel mloginviewmodel;

    private TextInputLayout txt_firstname, txt_lastname, txt_email, txt_password, txt_confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_firstname = findViewById(R.id.edt_firstname);
        txt_lastname = findViewById(R.id.edt_lastname);
        txt_email = findViewById(R.id.edt_email_signup);
        txt_password = findViewById(R.id.edt_password_signup);
        txt_confirmPassword = findViewById(R.id.edt_conpassword_signup);

        mloginviewmodel = ViewModelProviders.of(this).get(LoginViewModel.class);

        getSupportActionBar().hide();
    }


    public void register_sucessfull(View view) {

        String FirstName = txt_firstname.getEditText().getText().toString();
        String LastName = txt_lastname.getEditText().getText().toString();
        String email = txt_email.getEditText().getText().toString();
        String password = txt_password.getEditText().getText().toString();
        String confirmpassword = txt_confirmPassword.getEditText().getText().toString();

        LoginMain data = new LoginMain();
        if (!isValidEmail(email)) {

            txt_email.setError("Invalid Email");

        } else if (!isValidPassword(password)) {

            txt_password.setError("password length should be greater than 6");

        } else if (!password.equals(confirmpassword)) {

            txt_confirmPassword.setError("Password not matched");
        } else {

            data.setUFirstname(FirstName);
            data.setULastname(LastName);
            data.setUEmail(email);
            data.setPassword(password);
            //mloginviewmodel.insert(FirstName, LastName, email, password);
            Log.d("Mainactivity_insert","firstname"+ FirstName);
            mloginviewmodel.insert(data);
            Log.d("insert completed","lastname" + LastName);

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("signup", false).commit();
            Toast.makeText(getBaseContext(), "Register SuccessFully", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(SignUp.this,MainActivity.class);
            startActivity(login);
       }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
