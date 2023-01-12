package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;

public class CookOrderAction extends AppCompatActivity {
    private Cook cook;
    private Order order;
    private CookService cookService;
    private TextView cook_order_action_tv_id_fill;
    private TextView cook_order_action_tv_client_fill;
    private TextView cook_order_action_tv_cook_fill;
    private TextView cook_order_action_tv_meal_fill;
    private TextView cook_order_action_tv_status_fill;
    private Button cook_order_action_btn_approve;
    private Button cook_order_action_btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_order_action);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        cook = (Cook) bundle.getParcelable("cook");
        order = (Order) bundle.getParcelable("order");
        cookService = new CookService(this);
        cook_order_action_tv_id_fill = (TextView) findViewById(R.id.cook_order_action_tv_id_fill);
        cook_order_action_tv_client_fill = (TextView) findViewById(R.id.cook_order_action_tv_client_fill);
        cook_order_action_tv_cook_fill = (TextView) findViewById(R.id.cook_order_action_tv_cook_fill);
        cook_order_action_tv_meal_fill = (TextView) findViewById(R.id.cook_order_action_tv_meal_fill);
        cook_order_action_tv_status_fill = (TextView) findViewById(R.id.cook_order_action_tv_status_fill);
        cook_order_action_btn_approve = (Button) findViewById(R.id.cook_order_action_btn_approve);
        cook_order_action_btn_reject = (Button) findViewById(R.id.cook_order_action_btn_reject);

        cook_order_action_tv_id_fill.setText(order.getId());
        cook_order_action_tv_client_fill.setText(order.getClient());
        cook_order_action_tv_cook_fill.setText(order.getCook());
        cook_order_action_tv_meal_fill.setText(order.getMeal());
        cook_order_action_tv_status_fill.setText(order.getStatus());

        if(!order.getStatus().equals("PENDING")){
            cook_order_action_btn_approve.setVisibility(View.INVISIBLE);
            cook_order_action_btn_reject.setVisibility(View.INVISIBLE);
        }

        cook_order_action_btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isApproved = cookService.approveOrder(order);
                Toast.makeText(CookOrderAction.this, "approved:: " + isApproved, Toast.LENGTH_SHORT).show();
            }
        });

        cook_order_action_btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRejected = cookService.rejectOrder(order);
                Toast.makeText(CookOrderAction.this, "rejected:: " + isRejected, Toast.LENGTH_SHORT).show();
            }
        });
    }
}