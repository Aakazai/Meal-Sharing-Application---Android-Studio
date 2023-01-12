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
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;

import java.io.IOException;

public class ClientAllMeals extends AppCompatActivity {
    private Client client;
    private TextView client_meals_tv_name;
    private ListView client_all_meals_lv;
    private String meals;
    private ArrayAdapter<Meal> arrayAdapter;
    private MealService mealService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_all_meals);
        mealService = new MealService(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        meals = (String) bundle.getString("meals");
        client_all_meals_lv = (ListView) findViewById(R.id.client_all_meals_lv);
        client_meals_tv_name = (TextView) findViewById(R.id.client_meals_tv_name);

        try {
            if(meals.equals("all")){
                showAllMealsListView();
            }
            else if(meals.equals("breakfast")){
                client_meals_tv_name.setText("BREAKFAST");
                showBreakfastMealsListView();
            }
            else if(meals.equals("lunch")){
                client_meals_tv_name.setText("LUNCH");
                showLunchMealsListView();
            }
            else if(meals.equals("dinner")){
                client_meals_tv_name.setText("DINNER");
                showDinnerMealsListView();
            }
            else{
                client_meals_tv_name.setText(
                        meals.substring(0,1).toUpperCase() + meals.substring(1).toLowerCase());
                showCuisineMealsListView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        client_all_meals_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal meal = (Meal) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ClientMealAction.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("client", client);
                bundle.putParcelable("meal", meal);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showAllMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(this, android.R.layout.simple_list_item_1,
                mealService.getAllMeals()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_all_meals_lv.setAdapter(arrayAdapter);
    }

    private void showBreakfastMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(this, android.R.layout.simple_list_item_1,
                mealService.getAllMealType("breakfast")) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_all_meals_lv.setAdapter(arrayAdapter);
    }

    private void showLunchMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(this, android.R.layout.simple_list_item_1,
                mealService.getAllMealType("lunch")) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_all_meals_lv.setAdapter(arrayAdapter);
    }

    private void showDinnerMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(this, android.R.layout.simple_list_item_1,
                mealService.getAllMealType("dinner")) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_all_meals_lv.setAdapter(arrayAdapter);
    }

    private void showCuisineMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(this, android.R.layout.simple_list_item_1,
                mealService.getAllCuisineMeals(meals)) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_all_meals_lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if(meals.equals("all")){
                showAllMealsListView();
            }
            else if(meals.equals("breakfast")){
                showBreakfastMealsListView();
            }
            else if(meals.equals("lunch")){
                showLunchMealsListView();
            }
            else if(meals.equals("dinner")){
                showDinnerMealsListView();
            }
            else {
                showCuisineMealsListView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}