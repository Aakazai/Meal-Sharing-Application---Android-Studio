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

public class CookActiveMealAction extends AppCompatActivity {
    Cook cook;
    Meal meal;
    CookService cookService;
    private TextView cook_active_meal_action_tv_name_fill;
    private TextView cook_active_meal_action_tv_meal_type_fill;
    private TextView cook_active_meal_action_tv_cuisine_type_fill;
    private TextView cook_active_meal_action_tv_price_fill;
    private TextView cook_active_meal_action_tv_description_fill;
    private Button cook_active_meal_action_btn_unoffer;
    private Button cook_active_meal_action_btn_ingredients;
    private Button cook_active_meal_action_btn_allergens;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_active_meal_action);
        cookService = new CookService(CookActiveMealAction.this);
        cook_active_meal_action_tv_name_fill  = (TextView) findViewById(R.id.cook_active_meal_action_tv_name_fill);
        cook_active_meal_action_tv_meal_type_fill  = (TextView) findViewById(R.id.cook_active_meal_action_tv_meal_type_fill);
        cook_active_meal_action_tv_cuisine_type_fill  = (TextView) findViewById(R.id.cook_active_meal_action_tv_cuisine_type_fill);
        cook_active_meal_action_tv_price_fill  = (TextView) findViewById(R.id.cook_active_meal_action_tv_price_fill);
        cook_active_meal_action_tv_description_fill  = (TextView) findViewById(R.id.cook_active_meal_action_tv_description_fill);
        cook_active_meal_action_btn_unoffer  = (Button) findViewById(R.id.cook_active_meal_action_btn_unoffer);
        cook_active_meal_action_btn_ingredients  = (Button) findViewById(R.id.cook_active_meal_action_btn_ingredients);
        cook_active_meal_action_btn_allergens  = (Button) findViewById(R.id.cook_active_meal_action_btn_allergens);
        builder = new AlertDialog.Builder(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");
        meal = (Meal) bundle.getParcelable("meal");
        cook_active_meal_action_tv_name_fill.setText(meal.getMealName());
        cook_active_meal_action_tv_meal_type_fill.setText(meal.getMealType());
        cook_active_meal_action_tv_cuisine_type_fill.setText(meal.getCuisineType());
        cook_active_meal_action_tv_price_fill.setText(String.valueOf(meal.getPrice()));
        cook_active_meal_action_tv_description_fill.setText(meal.getDescription());
        Toast.makeText(CookActiveMealAction.this, "meal is:: " + meal.toString(), Toast.LENGTH_SHORT).show();

        cook_active_meal_action_btn_unoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean success = cookService.unOfferMeal(meal.getMealName(), cook.getEmail());
                    Toast.makeText(CookActiveMealAction.this, "unoffered?: " + success, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CookActiveMealAction.this, "ERROR IN UNOFFERING", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cook_active_meal_action_btn_ingredients.setOnClickListener(new View.OnClickListener() {
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
        cook_active_meal_action_btn_allergens.setOnClickListener(new View.OnClickListener() {
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
    }
    private static String stringer(List<String> list){
        return String.join(", ", list);
    }
}