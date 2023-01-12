package com.seg2105project.mealerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.seg2105project.mealerapp.user.User;
import com.seg2105project.mealerapp.user.UserService;

public class LoginPage extends AppCompatActivity {
    UserService userService;
    private Button user_btn_login;
    private EditText user_et_login;
    private EditText user_et_password;
    private TextView login_et_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        user_btn_login = (Button) findViewById(R.id.user_btn_login);
        user_et_login = (EditText) findViewById(R.id.user_et_login);
        user_et_password = (EditText) findViewById(R.id.user_et_password);
        login_et_error = (TextView) findViewById(R.id.login_et_error);

        user_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_et_error.setText(null);

                userService = new UserService(LoginPage.this);
                boolean success = userService.validateUser(user_et_login.getText().toString().toLowerCase(), user_et_password.getText().toString());

                if (success) {
                    User user = (User) userService.getUser(user_et_login.getText().toString().toLowerCase());
                    Intent intent = new Intent(LoginPage.this, Welcome.class);
                    intent.putExtra("user", user);
                    user_et_login.getText().clear();
                    user_et_password.getText().clear();
                    startActivity(intent);
                } else {
                    new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            login_et_error.setText("Username or password is incorrect");
                            login_et_error.setTextColor(Color.parseColor("#74112F"));
                        }

                        public void onFinish() {
                            login_et_error.setText("");
                        }
                    }.start();
                }
            }
        });
    }
}