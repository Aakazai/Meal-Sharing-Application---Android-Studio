package com.seg2105project.mealerapp.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.seg2105project.mealerapp.R;

public class AdminComplaints extends AppCompatActivity {
    private ListView admin_complaints_lv;
    private AdminService adminService;
    private ArrayAdapter<Complaint> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints);
        admin_complaints_lv = (ListView) findViewById(R.id.admin_complaints_lv);
        adminService = new AdminService(AdminComplaints.this);

        showComplaintsListView();

        admin_complaints_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint complaint = (Complaint) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), AdminComplaintAction.class);
                intent.putExtra("complaint", complaint);
                startActivity(intent);
            }
        });
    }

    private void showComplaintsListView() {
        arrayAdapter = new ArrayAdapter<Complaint>(AdminComplaints.this, android.R.layout.simple_list_item_1, adminService.getAllComplaints()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        admin_complaints_lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showComplaintsListView();
    }
}
