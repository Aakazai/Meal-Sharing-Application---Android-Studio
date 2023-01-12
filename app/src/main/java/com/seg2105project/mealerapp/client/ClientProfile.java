package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.client.Client;

public class ClientProfile extends AppCompatActivity {
    private Client client;
    private TextView client_profile_tv_name_fill;
    private TextView client_profile_tv_email_fill;
    private TextView client_profile_tv_address_fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");

        client_profile_tv_name_fill = (TextView) findViewById(R.id.client_profile_tv_name_fill);
        client_profile_tv_email_fill = (TextView) findViewById(R.id.client_profile_tv_email_fill);
        client_profile_tv_address_fill = (TextView) findViewById(R.id.client_profile_tv_address_fill);

        client_profile_tv_name_fill.setText(client.getFirst() + " " + client.getLast());
        client_profile_tv_email_fill.setText(client.getEmail());
        client_profile_tv_address_fill.setText(client.getAddress());
    }
}