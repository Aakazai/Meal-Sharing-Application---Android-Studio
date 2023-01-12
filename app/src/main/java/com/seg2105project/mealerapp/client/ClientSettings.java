package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seg2105project.mealerapp.LoginPage;
import com.seg2105project.mealerapp.MainActivity;
import com.seg2105project.mealerapp.R;

public class ClientSettings extends AppCompatActivity {
    private Button client_settings_btn_logout;
    private Client client;
    private Button client_settings_btn_all_meals;
    private Button client_settings_btn_meal_types;
    private Button client_settings_btn_cuisines;
    private Button client_settings_btn_orders;
    private Button client_settings_btn_all_profile;
    private TextView client_settings_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_settings);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        client_settings_btn_logout = (Button) findViewById(R.id.client_settings_btn_logout);
        client_settings_btn_all_meals = (Button) findViewById(R.id.client_settings_btn_all_meals);
        client_settings_btn_meal_types = (Button) findViewById(R.id.client_settings_btn_meal_types);
        client_settings_btn_cuisines = (Button) findViewById(R.id.client_settings_btn_cuisines);
        client_settings_btn_orders = (Button) findViewById(R.id.client_settings_btn_orders);
        client_settings_btn_all_profile = (Button) findViewById(R.id.client_settings_btn_all_profile);
        client_settings_welcome = (TextView) findViewById(R.id.client_settings_welcome);

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                client_settings_welcome.setText("Welcome " + client.getFirst() + " " + client.getLast() + "!");
                client_settings_welcome.setTextColor(Color.parseColor("#74112F"));
            }
            public void onFinish() {
                client_settings_welcome.setText("");
            }
        }.start();


        client_settings_btn_all_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientProfile.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });

        client_settings_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        client_settings_btn_all_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientAllMeals.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                bundleClient.putString("meals", "all");
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });
        client_settings_btn_meal_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientMealType.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });

        client_settings_btn_cuisines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientCuisines.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });

        client_settings_btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientOrders.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(intent);
    }
}