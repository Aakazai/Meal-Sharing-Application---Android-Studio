package com.seg2105project.mealerapp.cook;

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

public class CookAllMeals extends AppCompatActivity {
    private Cook cook;
    private MealService mealServicee;
    private CookService cookService;
    private ListView cook_all_meals_lv;
    private TextView cook_all_meals_tv_title;
    private ArrayAdapter<Meal> arrayAdapter;
    private String meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_all_meals);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mealServicee = new MealService(CookAllMeals.this);
        cookService = new CookService(CookAllMeals.this);
        cook = (Cook) bundle.getParcelable("cook");
        meals = (String) bundle.getString("meals");
        cook_all_meals_lv = (ListView) findViewById(R.id.cook_all_meals_lv);
        cook_all_meals_tv_title = (TextView) findViewById(R.id.cook_all_meals_tv_title);

        try {
            if(meals.equals("active")){
                showActiveMealsListView();
            }
            else {
                showInActiveMealsListView();
                cook_all_meals_tv_title.setText("INACTIVE MEALS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cook_all_meals_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal meal = (Meal) parent.getItemAtPosition(position);
                if(meals.equals("active")){
                    Intent intent = new Intent(getApplicationContext(), CookActiveMealAction.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("cook", cook);
                    bundle.putParcelable("meal", meal);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), CookInActiveMealAction.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("cook", cook);
                    bundle.putParcelable("meal", meal);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void showActiveMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(CookAllMeals.this, android.R.layout.simple_list_item_1,
                mealServicee.getAllOfferedMealsFromCook(cookService.getCook(cook.getEmail()).getActiveMeals())) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        cook_all_meals_lv.setAdapter(arrayAdapter);
    }

    private void showInActiveMealsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<Meal>(CookAllMeals.this, android.R.layout.simple_list_item_1,
                mealServicee.getAllUnOfferedMealsFromCook(cookService.getCook(cook.getEmail()).getUnOfferedMeals())) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        cook_all_meals_lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if(meals.equals("active")){
                showActiveMealsListView();
            }
            else {
                showInActiveMealsListView();
                cook_all_meals_tv_title.setText("INACTIVE MEALS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}