package com.devteam5.fun5helloworld.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.devteam5.fun5helloworld.R;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputEditText userName = findViewById(R.id.et_user_name);
                TextInputEditText userPhno = findViewById(R.id.et_user_phone_no);
                TextInputEditText userPass = findViewById(R.id.et_user_pass);
                String name = userName.getText().toString();
                String phno = userPhno.getText().toString();
                String pass = userPass.getText().toString();

                if (TextUtils.equals(name, "")) {
                    userName.setError("Fill Name");
                    return;
                }
                if (TextUtils.equals(phno, "")) {
                    userPhno.setError("Fill Phno");
                    return;
                }
                if (TextUtils.equals(pass, "")) {
                    userPass.setError("Fill Password");
                    return;
                }

                Snackbar.make(view, "Sign Up Complete", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }
}
