package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.LoginPage;
import com.seg2105project.mealerapp.MainActivity;
import com.seg2105project.mealerapp.R;

public class CookSettings extends AppCompatActivity {
    Cook cook;
    CookService cookService;
    private Button cook_menu_btn_active_meals;
    private Button cook_menu_btn_inactive_meals;
    private Button cook_menu_btn_add_meal;
    private Button cook_settings_btn_logout;
    private Button cook_menu_btn_orders;
    private Button cook_menu_btn_profile;
    private TextView cook_settings_tv_rating_fill;
    private TextView cook_settings_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");
        cookService = new CookService(CookSettings.this);
        cook_menu_btn_active_meals = (Button) findViewById(R.id.cook_menu_btn_active_meals);
        cook_menu_btn_inactive_meals = (Button) findViewById(R.id.cook_menu_btn_inactive_meals);
        cook_menu_btn_add_meal = (Button) findViewById(R.id.cook_menu_btn_add_meal);
        cook_settings_btn_logout = (Button) findViewById(R.id.cook_settings_btn_logout);
        cook_menu_btn_orders = (Button) findViewById(R.id.cook_menu_btn_orders);
        cook_menu_btn_profile = (Button) findViewById(R.id.cook_menu_btn_profile);
        cook_settings_tv_rating_fill = (TextView) findViewById(R.id.cook_settings_tv_rating_fill);

        cook_settings_welcome = (TextView) findViewById(R.id.cook_settings_welcome);

        try {
            boolean success = cookService.updateCook(cook);
//            Toast.makeText(CookSettings.this, success + " ADDED", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
//            e.printStackTrace();
            boolean bool = cook == null;
            Toast.makeText(CookSettings.this, "cook is null: " + bool, Toast.LENGTH_SHORT).show();
        }

        if(cook.getRating() > 0){
            cook_settings_tv_rating_fill.setText("Rating is: " + cook.getRating());
        }

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                cook_settings_welcome.setText("Welcome " + cook.getFirst() + " " + cook.getLast() + "!");
                cook_settings_welcome.setTextColor(Color.parseColor("#74112F"));
            }
            public void onFinish() {
                cook_settings_welcome.setText("");
            }
        }.start();

        cook_menu_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookProfile.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cook_menu_btn_active_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookAllMeals.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                bundle.putString("meals", "active");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cook_menu_btn_inactive_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookAllMeals.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                bundle.putString("meals", "inactive");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cook_menu_btn_add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookAddMeal.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cook_settings_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        cook_menu_btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookOrders.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                intent.putExtras(bundle);
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