package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.admin.AdminComplaintAction;
import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.client.Client;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;
import com.seg2105project.mealerapp.meal.Cuisine;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;

import java.io.IOException;

public class ClientCuisines extends AppCompatActivity {
    private Client client;
    private ListView client_cuisines_lv;
    private ArrayAdapter<String> arrayAdapter;
    private MealService mealService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cuisines);
        client_cuisines_lv = (ListView) findViewById(R.id.client_cuisines_lv);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        mealService = new MealService(this);

        try {
            showAllCuisinesListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        client_cuisines_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cuisine = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ClientAllMeals.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("client", client);
                bundle.putString("meals", cuisine.toLowerCase());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showAllCuisinesListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                mealService.getAllCuisines()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_cuisines_lv.setAdapter(arrayAdapter);
    }
    protected void onRestart() {
        super.onRestart();
        try {
            showAllCuisinesListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}