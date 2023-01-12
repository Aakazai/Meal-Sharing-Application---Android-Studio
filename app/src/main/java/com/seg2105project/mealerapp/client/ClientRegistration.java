package com.seg2105project.mealerapp.client;

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

public class ClientRegistration extends AppCompatActivity {

    private static final String TABLE = "CLIENT_TABLE";

    private Button client_btn_signup;
    private EditText client_et_first, client_et_last, client_et_email,
            client_et_address, client_et_password,
            client_et_creditcardnumber, client_et_creditcarddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup);
        client_btn_signup = (Button) findViewById(R.id.client_btn_signup);
        client_et_first = (EditText) findViewById(R.id.client_et_first);
        client_et_last = (EditText) findViewById(R.id.client_et_last);
        client_et_email = (EditText) findViewById(R.id.client_et_email);
        client_et_address = (EditText) findViewById(R.id.client_et_address);
        client_et_password = (EditText) findViewById(R.id.client_et_password);
        client_et_creditcardnumber = (EditText) findViewById(R.id.client_et_creditcardnumber);
        client_et_creditcarddate = (EditText) findViewById(R.id.client_et_creditcarddate);

        client_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean registerd = false;
                ClientService clientService = new ClientService(ClientRegistration.this);
                UserService userService = new UserService(ClientRegistration.this);

                String username = client_et_email.getText().toString().toLowerCase();

                if (userService.existsUser(username)) {
                    registerd = true;
                    client_et_email.setError("User already registerd");
                }

                if (!registerd) {
                    Client client;
                    User user;
                    try {
                        user = new User(username, client_et_password.getText().toString(), TABLE, "ACTIVE");
                        client = new Client(client_et_first.getText().toString(),
                                client_et_last.getText().toString(),
                                username,
                                client_et_address.getText().toString(),
                                client_et_creditcardnumber.getText().toString(),
                                client_et_creditcarddate.getText().toString());

//                        Toast.makeText(ClientRegistration.this, client.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
//                        Toast.makeText(ClientRegistration.this, "Error creating chef", Toast.LENGTH_SHORT).show();

                        user = new User("ERROR ON", "SIGN UP", "");

                        client = new Client("ERROR", "IN", "SIGNING",
                                "UP", "CLIENT", "");
                    }

                    boolean success = false;

                    try {
                        success = userService.addUser(user, TABLE) && clientService.addClient(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(ClientRegistration.this, "Success?= " + success, Toast.LENGTH_SHORT).show();
                    if (client_et_first.getText().length() == 0) {
                        client_et_first.setError("Field cannot be left blank");
                    } else if (client_et_last.getText().length() == 0) {
                        client_et_last.setError("Field cannot be left blank");
                    } else if (username.length() == 0) {
                        client_et_last.setError("Field cannot be left blank");
                    } else if (client_et_password.getText().length() == 0) {
                        client_et_creditcardnumber.setError("Field cannot be left blank");
                    } else if (client_et_address.getText().length() == 0) {
                        client_et_address.setError("Field cannot be left blank");
                    } else if (client_et_creditcardnumber.getText().length() == 0) {
                        client_et_creditcardnumber.setError("Field cannot be left blank");
                    } else if (client_et_creditcarddate.getText().length() == 0) {
                        client_et_last.setError("Field cannot be left blank");
                    } else if (client_et_creditcarddate.getText().length() == 0) {
                        client_et_last.setError("Field cannot be left blank");
                    } else {
                        Intent intent = new Intent(ClientRegistration.this, Welcome.class);

                        intent.putExtra("user", user);

                        client_et_first.getText().clear();
                        client_et_last.getText().clear();
                        client_et_email.getText().clear();
                        client_et_password.getText().clear();
                        client_et_address.getText().clear();
                        client_et_creditcardnumber.getText().clear();
                        client_et_creditcarddate.getText().clear();

                        startActivity(intent);
                    }
                }
            }
        });
    }
}