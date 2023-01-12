package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.meal.Meal;

import java.util.ArrayList;

public class ClientMealCooks extends AppCompatActivity {
    private Meal meal;
    private String cook;
    private TextView client_meal_cooks_tv_instruction;
    private TextView client_meal_cooks_tv_selected_cook;
    private ListView client_meal_cooks_lv;
    private Button client_meal_cooks_btn_save;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal_cooks);
        client_meal_cooks_tv_instruction = (TextView) findViewById(R.id.client_meal_cooks_tv_instruction);
        client_meal_cooks_tv_selected_cook = (TextView) findViewById(R.id.client_meal_cooks_tv_selected_cook);
        client_meal_cooks_btn_save = (Button) findViewById(R.id.client_meal_cooks_btn_save);
        client_meal_cooks_lv = (ListView) findViewById(R.id.client_meal_cooks_lv);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        meal = (Meal) bundle.getParcelable("meal");

        showCooksListView();

        client_meal_cooks_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cook = (String) parent.getItemAtPosition(position);
                client_meal_cooks_tv_instruction.setVisibility(View.INVISIBLE);
                client_meal_cooks_tv_selected_cook.setVisibility(View.VISIBLE);
                client_meal_cooks_tv_selected_cook.setText("Selected cook is: " + cook);
                showCooksListView();
                Toast.makeText(ClientMealCooks.this, "Cook is " + cook, Toast.LENGTH_SHORT).show();
            }
        });

        client_meal_cooks_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("cook", cook);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void showCooksListView() {
        arrayAdapter = new ArrayAdapter<String>(ClientMealCooks.this, android.R.layout.simple_list_item_1, meal.getCooks()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_meal_cooks_lv.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("cook", cook);
        setResult(RESULT_OK, data);
        finish();
    }
}