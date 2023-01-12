package com.seg2105project.mealerapp.cook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.Welcome;
import com.seg2105project.mealerapp.user.User;
import com.seg2105project.mealerapp.user.UserService;

import java.io.IOException;

public class CookRegistration extends AppCompatActivity {

    private static final String TABLE = "COOK_TABLE";

    private Button cook_btn_signup;
    private EditText cook_et_first, cook_et_last,
            cook_et_email, cook_et_address, cook_et_description, cook_et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_signup);

        cook_btn_signup = (Button) findViewById(R.id.cook_btn_signup);
        cook_et_first = (EditText) findViewById(R.id.cook_et_first);
        cook_et_last = (EditText) findViewById(R.id.cook_et_last);
        cook_et_email = (EditText) findViewById(R.id.cook_et_email);
        cook_et_address = (EditText) findViewById(R.id.cook_et_address);
        cook_et_description = (EditText) findViewById(R.id.cook_et_description);
        cook_et_password = (EditText) findViewById(R.id.cook_et_password);

        cook_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean registerd = false;
                CookService cookService = new CookService(CookRegistration.this);
                UserService userService = new UserService(CookRegistration.this);

                String username = cook_et_email.getText().toString().toLowerCase();
                if (userService.existsUser(username)) {
                    registerd = true;
                    cook_et_email.setError("User already registerd");
                }
                if (!registerd) {
                    User user;
                    Cook cook;
                    try {
                        user = new User(username, cook_et_password.getText().toString(), TABLE, "ACTIVE");
                        cook = new Cook(cook_et_first.getText().toString(),
                                cook_et_last.getText().toString(),
                                username,
                                cook_et_address.getText().toString(),
                                cook_et_description.getText().toString());

//                        Toast.makeText(CookRegistration.this, cook.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
//                        Toast.makeText(CookRegistration.this, "Error creating chef", Toast.LENGTH_SHORT).show();

                        user = new User("ERROR ON", "SIGN UP", "");
                        cook = new Cook("ERROR", "IN", "SIGNING", "UP", "CHEF");
                    }

                    boolean success = false;
                    try {
                        success = userService.addUser(user, TABLE) && cookService.addCook(cook);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(CookRegistration.this, "Success?= " + success, Toast.LENGTH_SHORT).show();
                    if (cook_et_first.getText().length() == 0) {
                        cook_et_first.setError("Field cannot be left blank");
                    } else if (cook_et_last.getText().length() == 0) {
                        cook_et_last.setError("Field cannot be left blank");
                    } else if (username.length() == 0) {
                        cook_et_last.setError("Field cannot be left blank");
                    } else if (cook_et_password.getText().length() == 0) {
                        cook_et_password.setError("Field cannot be left blank");
                    } else if (cook_et_address.getText().length() == 0) {
                        cook_et_address.setError("Field cannot be left blank");
                    } else if (cook_et_description.getText().length() == 0) {
                        cook_et_description.setError("Field cannot be left blank");
                    } else {
                        Intent intent = new Intent(CookRegistration.this, Welcome.class);

                        intent.putExtra("user", user);

                        cook_et_first.getText().clear();
                        cook_et_last.getText().clear();
                        cook_et_email.getText().clear();
                        cook_et_password.getText().clear();
                        cook_et_address.getText().clear();
                        cook_et_description.getText().clear();

                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void onClickImage(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);

    }
}