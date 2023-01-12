package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.meal.MealService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CookAddMeal extends AppCompatActivity {
    private EditText cook_add_meal_et_meal_name;
    private EditText cook_add_meal_et_cuisine_type;
    private EditText cook_add_meal_et_description;
    private EditText cook_add_meal_et_price;
    private Button cook_add_meal_btn_add;
    private Button cook_add_meal_btn_ingredients;
    private Button cook_add_meal_btn_allergens;
    private TextView cook_add_meal_tv_meal_exists;
    private TextView cook_add_meal_tv_meal_exists_click;
    private TextView cook_add_meal_tv_cook_meal_exists;
    private TextView cook_add_meal_tv_meal_type;
    private String mealType = null;
    private RadioButton cook_add_meal_radio_dinner;
    Meal meal = new Meal();
    Cook cook;
    CookService cookService;
    MealService mealService;

    ActivityResultLauncher<Intent> ingredientsActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            Intent intent = activityResult.getData();

                            if (resultCode == RESULT_OK) {
                                List<String> ingredients = intent.getStringArrayListExtra("ingredients");
                                meal.setIngredients(ingredients);
                                Toast.makeText(CookAddMeal.this, "got list", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CookAddMeal.this, "FAILED GET LIST", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );

    ActivityResultLauncher<Intent> allergensActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            Intent intent = activityResult.getData();

                            if (resultCode == RESULT_OK) {
                                List<String> allergens = intent.getStringArrayListExtra("allergens");
                                meal.setAllergens(allergens);
                                Toast.makeText(CookAddMeal.this, "got list", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CookAddMeal.this, "FAILED GET LIST", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_add_meal);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cookService = new CookService(CookAddMeal.this);
        mealService = new MealService(CookAddMeal.this);
        cook = (Cook) bundle.getParcelable("cook");
        cook_add_meal_et_meal_name = (EditText) findViewById(R.id.cook_add_meal_et_meal_name);
        cook_add_meal_radio_dinner = (RadioButton) findViewById(R.id.cook_add_meal_radio_dinner);
        cook_add_meal_et_cuisine_type = (EditText) findViewById(R.id.cook_add_meal_et_cuisine_type);
        cook_add_meal_et_description = (EditText) findViewById(R.id.cook_add_meal_et_description);
        cook_add_meal_et_price = (EditText) findViewById(R.id.cook_add_meal_et_price);
        cook_add_meal_btn_ingredients = (Button) findViewById(R.id.cook_add_meal_btn_ingredients);
        cook_add_meal_btn_allergens = (Button) findViewById(R.id.cook_add_meal_btn_allergens);
        cook_add_meal_btn_add = (Button) findViewById(R.id.cook_add_meal_btn_add);
        cook_add_meal_tv_meal_exists = (TextView) findViewById(R.id.cook_add_meal_tv_meal_exists);
        cook_add_meal_tv_meal_exists_click = (TextView) findViewById(R.id.cook_add_meal_tv_meal_exists_click);
        cook_add_meal_tv_cook_meal_exists = (TextView) findViewById(R.id.cook_add_meal_tv_cook_meal_exists);
        cook_add_meal_tv_meal_type = (TextView) findViewById(R.id.cook_add_meal_tv_meal_type);
        cook_add_meal_btn_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookAddMealIngredients.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                bundle.putStringArrayList("ingredients", (ArrayList<String>) meal.getIngredients());
                intent.putExtras(bundle);
                ingredientsActivityResultLauncher.launch(intent);
            }
        });
        cook_add_meal_btn_allergens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookAddMealAllergens.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                bundle.putStringArrayList("allergens", (ArrayList<String>) meal.getAllergens());
                intent.putExtras(bundle);
                allergensActivityResultLauncher.launch(intent);
            }
        });
        cook_add_meal_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meal.setMealName(cook_add_meal_et_meal_name.getText().toString());
                boolean exists = false;
                try {
                    exists = (cookService.getCook(cook.getEmail()).contains(meal.getMealName()));
                } catch (Exception e) {
                    Toast.makeText(CookAddMeal.this, "cannot verify", Toast.LENGTH_LONG).show();
                }

                boolean isValidated = true;
                if (cook_add_meal_et_meal_name.getText().length() == 0) {
                    cook_add_meal_et_meal_name.setError("Field cannot be left blank");
                    isValidated = false;
                }
                if(mealType == null){
                    cook_add_meal_tv_meal_type.setError("Field cannot be left blank");
                    isValidated = false;
                } if (cook_add_meal_et_cuisine_type.length() == 0) {
                    cook_add_meal_et_cuisine_type.setError("Field cannot be left blank");
                    isValidated = false;
                } if (cook_add_meal_et_description.getText().length() == 0) {
                    cook_add_meal_et_description.setError("Field cannot be left blank");
                    isValidated = false;
                } if (cook_add_meal_et_price.getText().length() == 0) {
                    cook_add_meal_et_price.setError("Field cannot be left blank");
                    isValidated = false;
                }

                if(isValidated){
                    Toast.makeText(CookAddMeal.this, "isvalidated " + isValidated, Toast.LENGTH_LONG).show();
                    if (exists) {
                        cook_add_meal_btn_add.setVisibility(View.INVISIBLE);
                        cook_add_meal_btn_allergens.setVisibility(View.INVISIBLE);
                        cook_add_meal_btn_ingredients.setVisibility(View.INVISIBLE);
                        cook_add_meal_tv_cook_meal_exists.setVisibility(View.VISIBLE);
                        cook_add_meal_et_meal_name.setEnabled(false);
                        cook_add_meal_et_cuisine_type.setEnabled(false);
                        cook_add_meal_et_description.setEnabled(false);
                        cook_add_meal_et_price.setEnabled(false);

                    } else if (cookService.existsMeal(meal.getMealName())) {
                        cook_add_meal_btn_add.setVisibility(View.INVISIBLE);
                        cook_add_meal_btn_allergens.setVisibility(View.INVISIBLE);
                        cook_add_meal_btn_ingredients.setVisibility(View.INVISIBLE);
                        cook_add_meal_tv_meal_exists.setVisibility(View.VISIBLE);
                        cook_add_meal_tv_meal_exists.setText("Meal exists");
                        cook_add_meal_tv_meal_exists.setTextColor(Color.parseColor("#74112F"));
                        cook_add_meal_tv_meal_exists_click.setVisibility(View.VISIBLE);
                        cook_add_meal_tv_meal_exists_click.setText("View Existing Meal");
                        cook_add_meal_tv_meal_exists_click.setTextColor(Color.parseColor("#74112F"));
                    } else {
                        meal.setMealName(cook_add_meal_et_meal_name.getText().toString());
                        meal.setMealType(mealType);
                        meal.setCuisineType(cook_add_meal_et_cuisine_type.getText().toString());
                        meal.setDescription(cook_add_meal_et_description.getText().toString());
                        meal.setPrice(Double.parseDouble(cook_add_meal_et_price.getText().toString()));
                        meal.addCook(cook.getEmail());
                        cook.addMeal(meal.getMealName());
                        try {
                            boolean sucess = cookService.addMeal(meal) && cookService.updateCook(cook);
                            Toast.makeText(CookAddMeal.this, "added? " + sucess, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    Toast.makeText(CookAddMeal.this, "isvalidated " + isValidated, Toast.LENGTH_LONG).show();
                }


            }
        });
        cook_add_meal_tv_meal_exists_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    meal = mealService.getMeal(meal.getMealName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), CookAddExistingMeal.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("meal", meal);
                bundle.putParcelable("cook", cook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.cook_add_meal_radio_breakfast:
                if (checked)
                    mealType = "breakfast";
                cook_add_meal_tv_meal_type.setError(null);
                    break;
            case R.id.cook_add_meal_radio_lunch:
                if (checked)
                    mealType = "lunch";
                cook_add_meal_tv_meal_type.setError(null);
                    break;
            case R.id.cook_add_meal_radio_dinner:
                if (checked)
                    mealType = "dinner";
                cook_add_meal_tv_meal_type.setError(null);
                    break;
        }
    }
}