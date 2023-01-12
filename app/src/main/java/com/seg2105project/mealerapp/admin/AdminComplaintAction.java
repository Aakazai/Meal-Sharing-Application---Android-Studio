package com.seg2105project.mealerapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105project.mealerapp.R;

public class AdminComplaintAction extends AppCompatActivity {
    private Complaint complaint;
    private AdminService adminService;
    private TextView admin_complaint_id_tv_fill;
    private TextView admin_complaint_client_tv_fill;
    private TextView admin_complaint_cook_tv_fill;
    private TextView admin_complaint_reason_tv_fill;
    private TextView admin_complaint_status_tv_fill;
    private Button admin_complaint_btn_dismiss;
    private Button admin_complaint_btn_suspend_tmp;
    private Button admin_complaint_btn_suspend_indef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint_action);
        complaint = (Complaint) getIntent().getExtras().getParcelable("complaint");
        adminService = new AdminService(AdminComplaintAction.this);
        admin_complaint_btn_dismiss = (Button) findViewById(R.id.admin_complaint_btn_dismiss);
        admin_complaint_btn_suspend_tmp = (Button) findViewById(R.id.admin_complaint_btn_suspend_tmp);
        admin_complaint_btn_suspend_indef = (Button) findViewById(R.id.admin_complaint_btn_suspend_indef);
        admin_complaint_id_tv_fill = (TextView) findViewById(R.id.admin_complaint_id_tv_fill);
        admin_complaint_client_tv_fill = (TextView) findViewById(R.id.admin_complaint_client_tv_fill);
        admin_complaint_cook_tv_fill = (TextView) findViewById(R.id.admin_complaint_id_cook_tv_fill);
        admin_complaint_reason_tv_fill = (TextView) findViewById(R.id.admin_complaint_reason_tv_fill);
        admin_complaint_status_tv_fill = (TextView) findViewById(R.id.admin_complaint_id_status_tv_fill);

        admin_complaint_id_tv_fill.setText(complaint.getID());
        admin_complaint_client_tv_fill.setText(complaint.getClient());
        admin_complaint_cook_tv_fill.setText(complaint.getCook());
        admin_complaint_reason_tv_fill.setText(complaint.getReason());
        admin_complaint_status_tv_fill.setText(complaint.getStatus());

        admin_complaint_btn_suspend_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool = false;
                try {
                    bool = adminService.suspendUserTemporarily(complaint.getCook());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                boolean bool2 = adminService.resolveComplaint(complaint.getID());
                Toast.makeText(AdminComplaintAction.this, "Suspended? " + complaint.getCook() + ": " + (bool && bool2), Toast.LENGTH_SHORT).show();
            }
        });
        admin_complaint_btn_suspend_indef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool = false;
                try {
                    bool = adminService.suspendUserIndefinitely(complaint.getCook());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                boolean bool2 = adminService.resolveComplaint(complaint.getID());
                Toast.makeText(AdminComplaintAction.this, "Suspended? " + complaint.getCook() + ": " + (bool && bool2), Toast.LENGTH_SHORT).show();
            }
        });
        admin_complaint_btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool = adminService.resolveComplaint(complaint.getID());
                Toast.makeText(AdminComplaintAction.this, "Dismissed? " + complaint.getCook() + ": " + bool, Toast.LENGTH_SHORT).show();
            }
        });

    }
}