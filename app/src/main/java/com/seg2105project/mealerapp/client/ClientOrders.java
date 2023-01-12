package com.seg2105project.mealerapp.client;

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

public class ClientOrders extends AppCompatActivity {
    private Client client;
    private ArrayAdapter<Order> arrayAdapter;
    private OrderService orderService;
    private ListView client_orders_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_orders);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        orderService = new OrderService(this);
        client = (Client) bundle.getParcelable("client");
        client_orders_lv = (ListView) findViewById(R.id.client_orders_lv);
        showClientOrdersListView();

        client_orders_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ClientOrderStatus.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("client", client);
                bundle.putParcelable("order", order);
                bundle.putString("str", "str");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showClientOrdersListView() {
        arrayAdapter = new ArrayAdapter<Order>(ClientOrders.this, android.R.layout.simple_list_item_1,
                orderService.getAllClientOrders(client.getEmail())) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.parseColor("#74112F"));
                return view;
            }
        };
        client_orders_lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showClientOrdersListView();
    }
}