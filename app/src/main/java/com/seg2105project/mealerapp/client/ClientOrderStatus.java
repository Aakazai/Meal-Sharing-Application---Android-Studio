package com.seg2105project.mealerapp.client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;

import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.cook.CookAddMealAllergens;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;
import com.seg2105project.mealerapp.cook.Cook;
import com.seg2105project.mealerapp.cook.CookService;

import java.util.ArrayList;

public class ClientOrderStatus extends AppCompatActivity {
    private Client client;
    private Order order;
    private Complaint complaint;
    private AdminService adminService;
    private OrderService orderService;
    private boolean isClientOrders = false;
    private TextView client_order_status_ID_fill;
    private TextView client_order_status_client_fill;
    private TextView client_order_status_cook_fill;
    private TextView client_order_status_meal_fill;
    private TextView client_order_status_status_fill;
    private TextView client_order_status_rating_fill;
    private TextView client_order_status_complaint_fill;
    private RadioGroup client_order_status_rg_rate;
    private Button client_order_status_btn_complain;
    private Button client_order_status_btn_submit;
    private TableRow client_order_status_tr_rating;
    private TableRow client_order_status_tr_complaint;
    private int rating = 0;
    private CookService cookService;
    private Cook cook;


    ActivityResultLauncher<Intent> complaintActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            Intent resultData = activityResult.getData();
                            if (resultCode == RESULT_OK) {
                                complaint = (Complaint) resultData.getParcelableExtra("complaint");
                                if(complaint != null){
                                    Toast.makeText(ClientOrderStatus.this, complaint.toString(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_status);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        order = (Order) bundle.getParcelable("order");
        adminService = new AdminService(this);
        cookService = new CookService(this);
        orderService = new OrderService(this);
        try {
            String str = (String) bundle.getString("str");
            isClientOrders = str != null;
        } catch (Exception exception){}
        client_order_status_ID_fill = (TextView) findViewById(R.id.client_order_status_ID_fill);
        client_order_status_client_fill = (TextView) findViewById(R.id.client_order_status_client_fill);
        client_order_status_cook_fill = (TextView) findViewById(R.id.client_order_status_cook_fill);
        client_order_status_meal_fill = (TextView) findViewById(R.id.client_order_status_meal_fill);
        client_order_status_status_fill = (TextView) findViewById(R.id.client_order_status_status_fill);
        client_order_status_rating_fill = (TextView) findViewById(R.id.client_order_status_rating_fill);
        client_order_status_complaint_fill = (TextView) findViewById(R.id.client_order_status_complaint_fill);
        client_order_status_rg_rate = (RadioGroup) findViewById(R.id.client_order_status_rg_rate);
        client_order_status_btn_complain = (Button) findViewById(R.id.client_order_status_btn_complain);
        client_order_status_btn_submit = (Button) findViewById(R.id.client_order_status_btn_submit);
        client_order_status_tr_rating = (TableRow) findViewById(R.id.client_order_status_tr_rating);
        client_order_status_tr_complaint = (TableRow) findViewById(R.id.client_order_status_tr_complaint);

        if(order.getStatus().equals("APPROVED")){
            if(order.getRating() == -1){
                client_order_status_rg_rate.setVisibility(View.VISIBLE);
                client_order_status_btn_submit.setVisibility(View.VISIBLE);
            }else {
                client_order_status_tr_rating.setVisibility(View.VISIBLE);
                client_order_status_rating_fill.setText(String.valueOf(order.getRating()));
            }
            if(order.getComplaint() == null){
                client_order_status_btn_complain.setVisibility(View.VISIBLE);
                client_order_status_btn_submit.setVisibility(View.VISIBLE);
            }else {
                client_order_status_tr_complaint.setVisibility(View.VISIBLE);
                client_order_status_complaint_fill.setText(order.getComplaint());
            }
        }


        client_order_status_ID_fill.setText(order.getId());
        client_order_status_client_fill.setText(order.getClient());
        client_order_status_cook_fill.setText(order.getCook());
        client_order_status_meal_fill.setText(order.getMeal());
        client_order_status_status_fill.setText(order.getStatus());

        client_order_status_btn_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClient = new Intent(getApplicationContext(), ClientComplaint.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                bundleClient.putParcelable("order", order);
                intentClient.putExtras(bundleClient);
                complaintActivityResultLauncher.launch(intentClient);
            }
        });

        client_order_status_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rating > 0){
                    try {
                        cook = cookService.getCook(order.getCook());
                        cook.addRating(rating);
                        order.setRating(rating);
                        Toast.makeText(ClientOrderStatus.this, "rating is:: " + cook.getRating(), Toast.LENGTH_SHORT).show();
                        boolean isUpdated = cookService.updateCook(cook);
                        Toast.makeText(ClientOrderStatus.this, "rating is:: " + cook.getRating() + ", isupdated:: " + isUpdated, Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(complaint != null){
                    boolean isAdded = adminService.addComplaint(complaint);
                    Toast.makeText(ClientOrderStatus.this, "isAdded " + isAdded, Toast.LENGTH_SHORT).show();
                    order.setComplaint(complaint.getID());
                }
                orderService.updateOrder(order);
                Intent intentClient = new Intent(getApplicationContext(), ClientSettings.class);
                Bundle bundleClient = new Bundle();
                bundleClient.putParcelable("client", client);
                intentClient.putExtras(bundleClient);
                startActivity(intentClient);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(isClientOrders){
            super.onBackPressed();
        }
        else {
            Intent intentClient = new Intent(getApplicationContext(), ClientSettings.class);
            Bundle bundleClient = new Bundle();
            bundleClient.putParcelable("client", client);
            intentClient.putExtras(bundleClient);
            startActivity(intentClient);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.client_order_status_radio_1:
                if (checked)
                    rating = 1;
                break;
            case R.id.client_order_status_radio_2:
                if (checked)
                    rating = 2;
                break;
            case R.id.client_order_status_radio_3:
                if (checked)
                    rating = 3;
                break;
            case R.id.client_order_status_radio_4:
                if (checked)
                    rating = 4;
                break;
            case R.id.client_order_status_radio_5:
                if (checked)
                    rating = 5;
                break;
        }
        Toast.makeText(ClientOrderStatus.this, "Rating is " + rating, Toast.LENGTH_SHORT).show();
    }
}