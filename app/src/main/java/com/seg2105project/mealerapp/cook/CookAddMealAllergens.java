package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.meal.MealService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CookAddMealAllergens extends AppCompatActivity {
    private Cook cook;
    private List<String> allergens;
    private MealService mealServicee;
    private ListView cook_add_meal_allergens_lv;
    private ArrayAdapter<String> arrayAdapter;
    private Button cook_add_meal_allergens_btn_add;
    private Button cook_add_meal_allergens_btn_save;
    private EditText cook_add_meal_allergens_et_add_allergen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_add_meal_allergens);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");
        allergens = (ArrayList<String>) bundle.getStringArrayList("allergens");
        mealServicee = new MealService(CookAddMealAllergens.this);
        cook_add_meal_allergens_lv = (ListView) findViewById(R.id.cook_add_meal_allergens_lv);
        cook_add_meal_allergens_btn_add = (Button) findViewById(R.id.cook_add_meal_allergens_btn_add);
        cook_add_meal_allergens_btn_save = (Button) findViewById(R.id.cook_add_meal_allergens_btn_save);
        cook_add_meal_allergens_et_add_allergen = (EditText) findViewById(R.id.cook_add_meal_allergens_et_add_allergen);

        try {
            showIngredientsListView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cook_add_meal_allergens_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cook_add_meal_allergens_et_add_allergen.getText().length() == 0) {
                    cook_add_meal_allergens_et_add_allergen.setError("Field cannot be left blank");}
                else{
                String ingredient = cook_add_meal_allergens_et_add_allergen.getText().toString();
                boolean isAdded = !(allergens.contains(ingredient.toLowerCase())) && (allergens.add(ingredient.toLowerCase()));
                try {
                    showIngredientsListView();
                    Toast.makeText(CookAddMealAllergens.this, "Added? " + isAdded, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cook_add_meal_allergens_et_add_allergen.getText().clear();
            }
        }});

        cook_add_meal_allergens_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putStringArrayListExtra("allergens", (ArrayList<String>) allergens);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        cook_add_meal_allergens_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ingredient = (String) parent.getItemAtPosition(position);
                boolean isRemoved = allergens.remove(ingredient);
                try {
                    showIngredientsListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(CookAddMealAllergens.this, "Deleted? " + isRemoved, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showIngredientsListView() throws IOException, ClassNotFoundException {
        arrayAdapter = new ArrayAdapter<String>(CookAddMealAllergens.this, android.R.layout.simple_list_item_1, allergens) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        cook_add_meal_allergens_lv.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putStringArrayListExtra("allergens", (ArrayList<String>) allergens);
        setResult(RESULT_OK, data);
        finish();
    }
}