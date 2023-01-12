package com.seg2105project.mealerapp.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.admin.Complaint;
import com.seg2105project.mealerapp.admin.AdminService;
import com.seg2105project.mealerapp.order.Order;

import java.util.ArrayList;

public class ClientComplaint extends AppCompatActivity {
    private Client client;
    private Order order;
    private Complaint complaint;
    private AdminService adminService;
    private TextView client_complaint_tv_client_fill;
    private TextView client_complaint_tv_cook_fill;
    private TextView client_complaint_tv_order_fill;
    private EditText client_complaint_et_reason;
    private Button client_complaint_btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_complaint);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        client = (Client) bundle.getParcelable("client");
        order = (Order) bundle.getParcelable("order");
        adminService = new AdminService(this);
        client_complaint_tv_client_fill = (TextView) findViewById(R.id.client_complaint_tv_client_fill);
        client_complaint_tv_cook_fill = (TextView) findViewById(R.id.client_complaint_tv_cook_fill);
        client_complaint_tv_order_fill = (TextView) findViewById(R.id.client_complaint_tv_order_fill);
        client_complaint_et_reason = (EditText) findViewById(R.id.client_complaint_et_reason);
        client_complaint_btn_submit = (Button) findViewById(R.id.client_complaint_btn_submit);

        client_complaint_tv_client_fill.setText(order.getClient());
        client_complaint_tv_cook_fill.setText(order.getCook());
        client_complaint_tv_order_fill.setText(order.getId());

        client_complaint_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (client_complaint_et_reason.getText().length() == 0) {
                    client_complaint_et_reason.setError("Field cannot be left blank");
                }
                if(client_complaint_et_reason.getText().length() > 0){
                    complaint = new Complaint(order.getClient(),
                            order.getCook(),
                            client_complaint_et_reason.getText().toString(),
                            order.getId());
                    Intent intentFinish = new Intent();
                    intentFinish.putExtra("complaint", complaint);
                    setResult(RESULT_OK, intentFinish);
                    finish();
//                    boolean isAdded = adminService.addComplaint(complaint);
//                    Toast.makeText(ClientComplaint.this, "isAdded " + isAdded, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(client_complaint_et_reason.getText().length() == 0){
            super.onBackPressed();
        }
        else {
            complaint = new Complaint(order.getClient(),
                    order.getCook(),
                    client_complaint_et_reason.getText().toString(),
                    order.getId());
            Intent intentFinish = new Intent();
            intentFinish.putExtra("complaint", complaint);
            setResult(RESULT_OK, intentFinish);
            finish();
        }
    }
}