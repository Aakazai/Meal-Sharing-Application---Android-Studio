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

import java.util.List;

public class CookInActiveMealAction extends AppCompatActivity {
    Cook cook;
    Meal meal;
    CookService cookService;
    private TextView cook_inactive_meal_action_tv_name_fill;
    private TextView cook_inactive_meal_action_tv_meal_type_fill;
    private TextView cook_inactive_meal_action_tv_cuisine_type_fill;
    private TextView cook_inactive_meal_action_tv_price_fill;
    private TextView cook_inactive_meal_action_tv_description_fill;
    private Button cook_inactive_meal_action_btn_reoffer;
    private Button cook_inactive_meal_action_btn_ingredients;
    private Button cook_inactive_meal_action_btn_allergens;
    private Button cook_inactive_meal_action_btn_delete;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_inactive_meal_action);
        cookService = new CookService(CookInActiveMealAction.this);
        cook_inactive_meal_action_tv_name_fill = (TextView) findViewById(R.id.cook_inactive_meal_action_tv_name_fill);
        cook_inactive_meal_action_tv_meal_type_fill = (TextView) findViewById(R.id.cook_inactive_meal_action_tv_meal_type_fill);
        cook_inactive_meal_action_tv_cuisine_type_fill = (TextView) findViewById(R.id.cook_inactive_meal_action_tv_cuisine_type_fill);
        cook_inactive_meal_action_tv_price_fill = (TextView) findViewById(R.id.cook_inactive_meal_action_tv_price_fill);
        cook_inactive_meal_action_tv_description_fill = (TextView) findViewById(R.id.cook_inactive_meal_action_tv_description_fill);
        cook_inactive_meal_action_btn_reoffer = (Button) findViewById(R.id.cook_inactive_meal_action_btn_reoffer);
        cook_inactive_meal_action_btn_ingredients = (Button) findViewById(R.id.cook_inactive_meal_action_btn_ingredients);
        cook_inactive_meal_action_btn_allergens = (Button) findViewById(R.id.cook_inactive_meal_action_btn_allergens);
        cook_inactive_meal_action_btn_delete = (Button) findViewById(R.id.cook_inactive_meal_action_btn_delete);
        builder = new AlertDialog.Builder(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");
        meal = (Meal) bundle.getParcelable("meal");
        cook_inactive_meal_action_tv_name_fill.setText(meal.getMealName());
        cook_inactive_meal_action_tv_meal_type_fill.setText(meal.getMealType());
        cook_inactive_meal_action_tv_cuisine_type_fill.setText(meal.getCuisineType());
        cook_inactive_meal_action_tv_price_fill.setText(String.valueOf(meal.getPrice()));
        cook_inactive_meal_action_tv_description_fill.setText(meal.getDescription());
        Toast.makeText(CookInActiveMealAction.this, "meal is:: " + meal.toString(), Toast.LENGTH_SHORT).show();

        cook_inactive_meal_action_btn_reoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean success = cookService.reOfferMeal(meal.getMealName(), cook.getEmail());
                    Toast.makeText(CookInActiveMealAction.this, "reoffered?: " + success, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CookInActiveMealAction.this, "ERROR IN REOFFERING", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cook_inactive_meal_action_btn_ingredients.setOnClickListener(new View.OnClickListener() {
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
        cook_inactive_meal_action_btn_allergens.setOnClickListener(new View.OnClickListener() {
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

        cook_inactive_meal_action_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isDeleted = cookService.deleteMeal(meal.getMealName(), cook.getEmail());
                    Toast.makeText(CookInActiveMealAction.this, "DELETED " + isDeleted, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(CookInActiveMealAction.this, "FAILED TO DELETE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static String stringer(List<String> list) {
        return String.join(", ", list);
    }
}