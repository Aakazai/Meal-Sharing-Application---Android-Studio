package com.seg2105project.mealerapp.client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.meal.Meal;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;

import java.util.List;

public class ClientMealAction extends AppCompatActivity {
    private Client client;
    private Meal meal;
    private String cook;
    private Order order;
    private OrderService orderService;
    private TableRow client_meal_action_row_cook_row;
    private TextView client_meal_action_tv_meal_name_fill;
    private TextView client_meal_action_tv_meal_type_fill;
    private TextView client_meal_action_tv_cuisine_type_fill;
    private TextView client_meal_action_tv_description_fill;
    private TextView client_meal_action_tv_price_fill;
    private TextView client_meal_action_tv_error;
    private TextView client_meal_action_tv_cook_fill;
    private Button client_meal_action_btn_ingredients;
    private Button client_meal_action_btn_allergens;
    private Button client_meal_action_btn_cook;
    private Button client_meal_action_btn_cook_details;
    private Button client_meal_action_btn_purchase;
    private AlertDialog.Builder builder;

    ActivityResultLauncher<Intent> cookActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int result = activityResult.getResultCode();
                            Intent data = activityResult.getData();
                            if (result == RESULT_OK) {
                                cook = (String) data.getStringExtra("cook");
                                if(cook != null){
                                    client_meal_action_btn_cook.setVisibility(View.INVISIBLE);
                                    client_meal_action_btn_cook_details.setVisibility(View.VISIBLE);
                                    client_meal_action_row_cook_row.setVisibility(View.VISIBLE);
                                    client_meal_action_tv_cook_fill.setText(cook);
                                }
                                Toast.makeText(ClientMealAction.this, "got cook " + cook, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ClientMealAction.this, "FAILED GET COOK", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal_action);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        meal = (Meal) bundle.getParcelable("meal");
        builder = new AlertDialog.Builder(this);
        orderService = new OrderService(this);
        client_meal_action_row_cook_row = (TableRow) findViewById(R.id.client_meal_action_row_cook_row);
        client_meal_action_tv_meal_name_fill = (TextView) findViewById(R.id.client_meal_action_tv_meal_name_fill);
        client_meal_action_tv_meal_type_fill = (TextView) findViewById(R.id.client_meal_action_tv_meal_type_fill);
        client_meal_action_tv_cuisine_type_fill = (TextView) findViewById(R.id.client_meal_action_tv_cuisine_type_fill);
        client_meal_action_tv_description_fill = (TextView) findViewById(R.id.client_meal_action_tv_description_fill);
        client_meal_action_tv_price_fill = (TextView) findViewById(R.id.client_meal_action_tv_price_fill);
        client_meal_action_tv_error = (TextView) findViewById(R.id.client_meal_action_tv_error);
        client_meal_action_tv_cook_fill = (TextView) findViewById(R.id.client_meal_action_tv_cook_fill);
        client_meal_action_btn_ingredients = (Button) findViewById(R.id.client_meal_action_btn_ingredients);
        client_meal_action_btn_allergens = (Button) findViewById(R.id.client_meal_action_btn_allergens);
        client_meal_action_btn_cook = (Button) findViewById(R.id.client_meal_action_btn_cook);
        client_meal_action_btn_cook_details = (Button) findViewById(R.id.client_meal_action_btn_cook_details);
        client_meal_action_btn_purchase = (Button) findViewById(R.id.client_meal_action_btn_purchase);

        client_meal_action_tv_meal_name_fill.setText(meal.getMealName());
        client_meal_action_tv_meal_type_fill.setText(meal.getMealType());
        client_meal_action_tv_cuisine_type_fill.setText(meal.getCuisineType());
        client_meal_action_tv_description_fill.setText(meal.getDescription());
        client_meal_action_tv_price_fill.setText(String.valueOf(meal.getPrice()));

        if(meal.getCooks().size() == 1){
            cook = meal.getCooks().get(0);
            client_meal_action_btn_cook_details.setVisibility(View.VISIBLE);
            client_meal_action_row_cook_row.setVisibility(View.VISIBLE);
            client_meal_action_tv_cook_fill.setText(cook);
            Toast.makeText(ClientMealAction.this, "cook is " + cook, Toast.LENGTH_LONG).show();
        }
        else client_meal_action_btn_cook.setVisibility(View.VISIBLE);

        client_meal_action_btn_ingredients.setOnClickListener(new View.OnClickListener() {
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
        client_meal_action_btn_allergens.setOnClickListener(new View.OnClickListener() {
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

        client_meal_action_btn_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientMealCooks.class); // make activity
                Bundle bundle = new Bundle();
                bundle.putParcelable("meal", meal);
                intent.putExtras(bundle);
                cookActivityResultLauncher.launch(intent);
            }
        });

        client_meal_action_btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cook == null){
                    client_meal_action_tv_error.setVisibility(View.VISIBLE);
                    client_meal_action_tv_error.setText("Cannot purchase until select a cook.");
                }
                else {
                    client_meal_action_tv_error.setVisibility(View.INVISIBLE);
                    client_meal_action_tv_error.setText("");
                    order = new Order(client.getEmail(), cook, meal.getMealName());
                    orderService.addOrder(order);
                    Intent intent = new Intent(getApplicationContext(), ClientOrderStatus.class); // make activity
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("client", client);
                    bundle.putParcelable("order", order);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        client_meal_action_btn_cook_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientCookDetails.class); // make activity
                Bundle bundle = new Bundle();
                bundle.putParcelable("client", client);
                bundle.putString("cook", cook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private static String stringer(List<String> list){
        return String.join(", ", list);
    }
}