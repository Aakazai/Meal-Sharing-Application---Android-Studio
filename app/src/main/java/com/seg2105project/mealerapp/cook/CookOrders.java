package com.seg2105project.mealerapp.cook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.seg2105project.mealerapp.R;
import com.seg2105project.mealerapp.order.Order;
import com.seg2105project.mealerapp.order.OrderService;

public class CookOrders extends AppCompatActivity {
    private Cook cook;
    private ArrayAdapter<Order> arrayAdapter;
    private OrderService orderService;
    private ListView cook_orders_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_orders);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        orderService = new OrderService(this);
        cook = (Cook) bundle.getParcelable("cook");
        cook_orders_lv = (ListView) findViewById(R.id.cook_orders_lv);
        showCookOrdersListView();

        cook_orders_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), CookOrderAction.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cook", cook);
                bundle.putParcelable("order", order);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showCookOrdersListView() {
        arrayAdapter = new ArrayAdapter<Order>(CookOrders.this, android.R.layout.simple_list_item_1,
                orderService.getAllCookOrders(cook.getEmail())) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        cook_orders_lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showCookOrdersListView();
    }
}