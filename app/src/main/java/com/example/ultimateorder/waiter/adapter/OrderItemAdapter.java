package com.example.ultimateorder.adapter;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ultimateorder.R;
import com.example.ultimateorder.model.OrderItem;
import java.util.ArrayList;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> implements View.OnClickListener {
    private ArrayList<OrderItem> OrderItems;
    private Context mContext;
    private OrderItem currentOrderItem;

    public OrderItemAdapter(ArrayList<OrderItem> data, Context context) {
        super(context, R.layout.orders_item_layout, data);
        this.OrderItems = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        openDialog(currentOrderItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.orders_item_layout, parent, false);

        OrderItem currentM = OrderItems.get(position);

        TextView id = (TextView)listItem.findViewById(R.id.id);
        id.setText(" Order #" + String.valueOf(currentM.getId()));

        TextView table = (TextView)listItem.findViewById(R.id.table);

        String[] path = currentM.getTable().getPath().split("/");
        table.setText("Table: " + String.valueOf(path[1].toString()));

        View finalListItem = listItem;

        TextView totalPrice = (TextView) listItem.findViewById(R.id.price);
        totalPrice.setText("Total: " + String.valueOf(currentM.getPrice()) + " RON");
        currentOrderItem = currentM;
        listItem.setOnClickListener(this);
        return listItem;
    }

    @SuppressLint("SetTextI18n")
    public void openDialog(OrderItem orderItem) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        // Set Custom Title
        TextView title = new TextView(getContext());
        // Title Properties
        title.setText("Order info");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(getContext());
        // Message Properties
        msg.setText("Order: " + orderItem.getId() + "\n" + "Total Price: " + orderItem.getPrice() + " RON\n");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        ToggleButton finished = new ToggleButton(getContext());
        alertDialog.setView(finished);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "SAVE CHANGES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(getContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }
}
