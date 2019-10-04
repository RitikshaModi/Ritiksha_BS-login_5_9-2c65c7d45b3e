package com.example.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private TextInputLayout txt_email,txt_password;
    private Button signup_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup_text = findViewById(R.id.btn_signup);
        txt_email = findViewById(R.id.text_input_username);
        txt_password = findViewById(R.id.edt_password);

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent_register);
            }
        });

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        getSupportActionBar().hide();
    }

    public void Login_successfull(View view) throws ExecutionException, InterruptedException {

        String email = txt_email.getEditText().getText().toString().trim();
        String password = txt_password.getEditText().getText().toString();

        //Log.d("printinggouiik7gggggggg", String.valueOf(email != null));

        if(!TextUtils.isEmpty(email)) {
            Log.d("email_login",email);
            if ( !TextUtils.isEmpty(password)) {
                Log.d("password", password);
                boolean isValid = loginViewModel.checkValidLogin(email, password);

                if (isValid) {
                    String f_name = loginViewModel.firstname(email);
                    Toast.makeText(getBaseContext(), "Successfully Logged In!", Toast.LENGTH_LONG).show();
                    Log.i("Successful_Login", "Login was successful");
                    Intent intent_home = new Intent(MainActivity.this, HomeActivity.class);
//
//                    intent_home.putExtra("email", email);
                    ((LoginApplication)this.getApplication()).setEmail(email);
                    ((LoginApplication)this.getApplication()).setname(f_name);
                    startActivity(intent_home);
                } else {
                    Toast.makeText(getBaseContext(), "Invalid Login!", Toast.LENGTH_SHORT).show();
                    Log.i("Unsuccessful_Login", "Login was not successful");
                }
            }
            else {
                //txt_password.setError("password required to login");
                Toast.makeText(getBaseContext(), "password required to login", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //txt_email.setError("email is required to login");
            Toast.makeText(getBaseContext(), "email is required to login", Toast.LENGTH_SHORT).show();
        }

    }
}