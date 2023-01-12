package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.seg2105project.mealerapp.R;

public class CookProfile extends AppCompatActivity {
    private Cook cook;
    private TextView cook_profile_tv_name_fill;
    private TextView cook_profile_tv_email_fill;
    private TextView cook_profile_tv_address_fill;
    private TextView cook_profile_tv_description_fill;
    private TextView cook_profile_tv_rating_fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");

        cook_profile_tv_name_fill = (TextView) findViewById(R.id.cook_profile_tv_name_fill);
        cook_profile_tv_email_fill = (TextView) findViewById(R.id.cook_profile_tv_email_fill);
        cook_profile_tv_address_fill = (TextView) findViewById(R.id.cook_profile_tv_address_fill);
        cook_profile_tv_description_fill = (TextView) findViewById(R.id.cook_profile_tv_description_fill);
        cook_profile_tv_rating_fill = (TextView) findViewById(R.id.cook_profile_tv_rating_fill);

        cook_profile_tv_name_fill.setText(cook.getFirst() + " " + cook.getLast());
        cook_profile_tv_email_fill.setText(cook.getEmail());
        cook_profile_tv_address_fill.setText(cook.getAddress());
        cook_profile_tv_description_fill.setText(cook.getDescription());

        if(cook.getRating() > 0){
            cook_profile_tv_rating_fill.setText(String.valueOf(cook.getRating()));
        }
    }
}