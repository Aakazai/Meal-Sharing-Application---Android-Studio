package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Meal;

public class ClientCookDetails extends AppCompatActivity {
    private Client client;
    private Cook cook;
    private CookService cookService;
    private TextView client_cook_details_tv_name_fill;
    private TextView client_cook_details_tv_email_fill;
    private TextView client_cook_details_tv_address_fill;
    private TextView client_cook_details_tv_description_fill;
    private TextView client_cook_details_tv_rating_fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cook_details);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        cookService = new CookService(this);
        try {
            cook = cookService.getCook((String) bundle.getString("cook"));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        client_cook_details_tv_name_fill = (TextView) findViewById(R.id.client_cook_details_tv_name_fill);
        client_cook_details_tv_email_fill = (TextView) findViewById(R.id.client_cook_details_tv_email_fill);
        client_cook_details_tv_address_fill = (TextView) findViewById(R.id.client_cook_details_tv_address_fill);
        client_cook_details_tv_description_fill = (TextView) findViewById(R.id.client_cook_details_tv_description_fill);
        client_cook_details_tv_rating_fill = (TextView) findViewById(R.id.client_cook_details_tv_rating_fill);

        client_cook_details_tv_name_fill.setText(cook.getFirst() + " " + cook.getLast());
        client_cook_details_tv_email_fill.setText(cook.getEmail());
        client_cook_details_tv_address_fill.setText(cook.getAddress());
        client_cook_details_tv_description_fill.setText(cook.getDescription());

        if(cook.getRating() > 0){
            client_cook_details_tv_rating_fill.setText(String.valueOf(cook.getRating()));
        }

    }
}