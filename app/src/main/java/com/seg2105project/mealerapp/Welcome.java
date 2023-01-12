package com.seg2105project.mealerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.seg2105project.mealerapp.admin.AdminComplaints;
import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.client.ClientService;
import com.seg2105project.mealerapp.client.ClientSettings;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.cook.CookSettings;
import com.seg2105project.mealerapp.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Welcome extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private AdminService adminService;
    private CookService cookService;
    private ClientService clientService;
    private Button welcome_btn_logout;
    private Button welcome_btn_admin_complaints;
    private TextView welcomeMessage;
    private TextView welcomeMessage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        constraintLayout = (ConstraintLayout) findViewById(R.id.welcomeLayout);
        welcome_btn_logout = (Button) findViewById(R.id.welcome_btn_logout);
        welcome_btn_admin_complaints = (Button) findViewById(R.id.welcome_btn_admin_complaints);
        User user = (User) getIntent().getExtras().getParcelable("user");
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        welcomeMessage2 = (TextView) findViewById(R.id.welcomeMessage2);

        switch (user.getType()) {
            case "CLIENT_TABLE":
                clientService = new ClientService(Welcome.this);
                Client client = null;
                try {
                    client = clientService.getClient(user.getID());
                } catch (Exception e) {
                    Toast.makeText(Welcome.this, "ERROR LOADING", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                Intent intentClient = new Intent(getApplicationContext(), ClientSettings.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
                break;
            case "COOK_TABLE":
                switch (user.getStatus()) {
                    case "ACTIVE":
                        cookService = new CookService(Welcome.this);
                        Cook cook = null;
                        try {
                            cook = cookService.getCook(user.getID());
                        } catch (Exception e) {
                            Toast.makeText(Welcome.this, "ERROR LOADING", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Intent intentCook = new Intent(getApplicationContext(), CookSettings.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("cook", cook);
                        intentCook.putExtras(bundle);
                        startActivity(intentCook);
                        break;
                    case "SUSPENDED":
                        constraintLayout.setBackgroundColor(Color.parseColor("#000000"));
                        welcomeMessage.setText(user.getID() + ", you are suspended indefinetly as a cook.");
                        welcomeMessage.setTextColor(Color.parseColor("#74112F"));
                        break;
                    default:
                        if (Long.parseLong(user.getStatus()) < System.currentTimeMillis()) {
                            adminService = new AdminService(Welcome.this);
                            try {
                                adminService.unSuspendUser(user.getID());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            welcomeMessage.setText("Welcome! Signed in as a cook.");
                        } else {
                            welcomeMessage2.setVisibility(View.VISIBLE);
                            constraintLayout.setBackgroundColor(Color.parseColor("#000000"));
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                            Date resultdate = new Date(Long.parseLong(user.getStatus()));
                            String end = sdf.format(resultdate);
                            welcomeMessage2.setText(user.getID() + ", you have been temporarily suspended.");
                            welcomeMessage2.setTextColor(Color.parseColor("#74112F"));
                            welcomeMessage.setText("Your suspendsion ends on: " + end + ".");
                            welcomeMessage.setTextColor(Color.parseColor("#74112F"));
                        }
                        break;
                }
                break;
            default:
                welcomeMessage.setText("Welcome! Signed in as admin.");
                welcome_btn_admin_complaints.setVisibility(View.VISIBLE);
        }
        welcome_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        welcome_btn_admin_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminComplaints.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(intent);
    }
}