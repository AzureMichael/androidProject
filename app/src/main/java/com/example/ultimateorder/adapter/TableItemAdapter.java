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
import android.widget.TextView;

import com.example.ultimateorder.model.TableItem;
import com.example.ultimateorder.R;

import java.util.ArrayList;

public class TableItemAdapter extends ArrayAdapter<TableItem> implements View.OnClickListener {
    private ArrayList<TableItem> TableItems;
    Context mContext;

    public TableItemAdapter(ArrayList<TableItem> data, Context context) {
        super(context, R.layout.table_item_layout, data);
        this.TableItems = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) { openDialog(); }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.table_item_layout, parent, false);

        TableItem currentM = TableItems.get(position);

        TextView id = (TextView)listItem.findViewById(R.id.id);
        id.setText("Table: " + String.valueOf(currentM.getId()));

        TextView seats = (TextView) listItem.findViewById(R.id.seats);
        seats.setText("Seats: " + String.valueOf(currentM.getSeats()));

        TextView isOccupied = (TextView) listItem.findViewById(R.id.isOccupied);
        isOccupied.setText("Occupied: " + String.valueOf(currentM.isOccupied()));

        TextView isReserved = (TextView) listItem.findViewById(R.id.isReserved);
        isReserved.setText("Reserved: " + String.valueOf(currentM.isReserved()));

        TextView totalPrice = (TextView) listItem.findViewById(R.id.total);
        totalPrice.setText("Total: " + String.valueOf(currentM.getTotalPrice()));

        View finalListItem = listItem;

        listItem.setOnClickListener(this);
        return listItem;
    }

    private void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        // Set Custom Title
        TextView title = new TextView(getContext());
        // Title Properties
        title.setText("Table Info");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(getContext());
        // Message Properties
        //msg.setText("Table: " + tableItem.getId()...);
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE", new DialogInterface.OnClickListener() {
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
        okBT.setTextColor(Color.GREEN);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.GRAY);
        cancelBT.setLayoutParams(negBtnLP);
    }
}
