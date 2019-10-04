package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {


    private EditText edtnewpassword, edtrenewpassword;
    private LoginViewModel loginViewModel;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtnewpassword = findViewById(R.id.edt_newpassword);
        edtrenewpassword = findViewById(R.id.edt_renewpassword);
        btn_reset = findViewById(R.id.btn_changepassword);

        final String email = ((LoginApplication) this.getApplication()).getEmail();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resetpassword = edtnewpassword.getText().toString();
                String confresetpassword = edtrenewpassword.getText().toString();

                if (isValidPassword(resetpassword)) {
                    if (!resetpassword.equals(confresetpassword)) {
                        edtrenewpassword.setError("Password not matched");
                    } else {
                        //String newpassword = edtnewpassword.getText().toString();
                        Log.d("password", resetpassword);
                        loginViewModel.update(resetpassword, email);
                        Log.d("password updated ", resetpassword);
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        Toast.makeText(getApplicationContext(), "Password Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    edtnewpassword.setError("password length should be greater than 6");
                }
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

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
