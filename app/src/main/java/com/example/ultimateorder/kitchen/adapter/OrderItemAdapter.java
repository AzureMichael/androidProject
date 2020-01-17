package com.example.ultimateorder.kitchen.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ultimateorder.R;
import com.example.ultimateorder.kitchen.OrderViewModelKitchen;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.model.OrderItem;
import com.example.ultimateorder.waiter.ui.orders.OrdersViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> implements View.OnClickListener {
    private List<OrderItem> OrderItems;
    private Context mContext;
    private OrderItem currentOrderItem;
    OrderViewModelKitchen orderViewModel;

    public OrderItemAdapter(List<OrderItem> data, Context context, OrderViewModelKitchen orderViewModel) {
        super(context, R.layout.orders_item_layout, data);
        this.OrderItems = data;
        this.mContext = context;
        this.orderViewModel = orderViewModel;
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.orders_item_layout, parent, false);

        OrderItem currentM = OrderItems.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.id);
        id.setText(" Order #" + String.valueOf(currentM.getId()));

        TextView table = (TextView) listItem.findViewById(R.id.table);

        String[] path = currentM.getTable().getPath().split("/");
        table.setText("Table: " + String.valueOf(path[1].toString()));

        View finalListItem = listItem;

        TextView totalPrice = (TextView) listItem.findViewById(R.id.price);
        totalPrice.setText("Total: " + String.valueOf(currentM.getPrice()) + " RON");
        currentOrderItem = currentM;
        listItem.setOnClickListener(this);
        return listItem;
    }

}