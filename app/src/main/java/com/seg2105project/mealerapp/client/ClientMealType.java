package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.seg2105project.mealerapp.R;

public class ClientMealType extends AppCompatActivity {
    private Client client;
    private Button client_meal_type_btn_breakfast;
    private Button client_meal_type_btn_lunch;
    private Button client_meal_type_btn_dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal_type);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        client_meal_type_btn_breakfast = (Button) findViewById(R.id.client_meal_type_btn_breakfast);
        client_meal_type_btn_lunch = (Button) findViewById(R.id.client_meal_type_btn_lunch);
        client_meal_type_btn_dinner = (Button) findViewById(R.id.client_meal_type_btn_dinner);

        client_meal_type_btn_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientAllMeals.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                bundleClient.putString("meals", "breakfast");
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });
        client_meal_type_btn_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientAllMeals.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                bundleClient.putString("meals", "lunch");
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });
        client_meal_type_btn_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientAllMeals.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                bundleClient.putString("meals", "dinner");
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });
    }
}