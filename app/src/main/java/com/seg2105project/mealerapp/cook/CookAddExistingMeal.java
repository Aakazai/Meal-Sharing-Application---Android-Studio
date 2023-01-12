package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.meal.Meal;

import java.io.IOException;
import java.util.List;

public class CookAddExistingMeal extends AppCompatActivity {
    private TextView cook_exist_meal_action_tv_name_fill;
    private TextView cook_exist_meal_action_tv_meal_type_fill;
    private TextView cook_exist_meal_action_tv_cuisine_type_fill;
    private TextView cook_exist_meal_action_tv_price_fill;
    private TextView cook_exist_meal_action_tv_description_fill;
    private Button cook_exist_meal_action_btn_add;
    private Button cook_exist_meal_action_btn_ingredients;
    private Button cook_exist_meal_action_btn_allergens;
    private boolean isAdded;
    private AlertDialog.Builder builder;
    Meal meal;
    Cook cook;
    CookService cookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_add_existing_meal);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cookService = new CookService(CookAddExistingMeal.this);
        cook = (Cook) bundle.getParcelable("cook");
        meal = (Meal) bundle.getParcelable("meal");
        isAdded = false;
        builder = new AlertDialog.Builder(this);
        cook_exist_meal_action_tv_name_fill = (TextView) findViewById(R.id.cook_exist_meal_action_tv_name_fill);
        cook_exist_meal_action_tv_meal_type_fill = (TextView) findViewById(R.id.cook_exist_meal_action_tv_meal_type_fill);
        cook_exist_meal_action_tv_cuisine_type_fill = (TextView) findViewById(R.id.cook_exist_meal_action_tv_cuisine_type_fill);
        cook_exist_meal_action_tv_description_fill = (TextView) findViewById(R.id.cook_exist_meal_action_tv_description_fill);
        cook_exist_meal_action_tv_price_fill = (TextView) findViewById(R.id.cook_exist_meal_action_tv_price_fill);
        cook_exist_meal_action_btn_ingredients = (Button) findViewById(R.id.cook_exist_meal_action_btn_ingredients);
        cook_exist_meal_action_btn_allergens = (Button) findViewById(R.id.cook_exist_meal_action_btn_allergens);
        cook_exist_meal_action_btn_add = (Button) findViewById(R.id.cook_exist_meal_action_btn_add);

        cook_exist_meal_action_tv_name_fill.setText(meal.getMealName());
        cook_exist_meal_action_tv_meal_type_fill.setText(meal.getMealType());
        cook_exist_meal_action_tv_cuisine_type_fill.setText(meal.getCuisineType());
        cook_exist_meal_action_tv_description_fill.setText(meal.getDescription());
        cook_exist_meal_action_tv_price_fill.setText(String.valueOf(meal.getPrice()));
        cook_exist_meal_action_btn_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Ingredients")
                        .setMessage(stringer(meal.getIngredients()))
                        .setCancelable(true)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        cook_exist_meal_action_btn_allergens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Allergens")
                        .setMessage(stringer(meal.getAllergens()))
                        .setCancelable(true)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        cook_exist_meal_action_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meal.addCook(cook.getEmail());
                cook.addMeal(meal.getMealName());
                try {
                    boolean sucess = cookService.addCookToMeal(meal) && cookService.updateCook(cook);
                    Toast.makeText(CookAddExistingMeal.this, "added? " + sucess, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                try {
//                    boolean sucess = cookService.addCookToMeal(meal, cook.getEmail());
//
//                    Toast.makeText(CookAddExistingMeal.this, "added? " + sucess, Toast.LENGTH_LONG).show();
//                    isAdded = true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private static String stringer(List<String> list) {
        return String.join(", ", list);
    }

    @Override
    public void onBackPressed() {
        if (isAdded) {
            Intent intent = new Intent(getApplicationContext(), CookSettings.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("cook", cook);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }
}