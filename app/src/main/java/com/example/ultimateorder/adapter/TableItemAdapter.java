package com.example.ultimateorder.adapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ultimateorder.model.TableItem;
import com.example.ultimateorder.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TableItemAdapter extends ArrayAdapter<TableItem> implements View.OnClickListener {
    private ArrayList<TableItem> TableItems;
    Context mContext;
    TableItem currentTableItem;
    private FirebaseFirestore firebaseFirestore;


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

        RelativeLayout layout = (RelativeLayout) listItem.findViewById(R.id.TableItemLayout);
        TableItem currentM = TableItems.get(position);
        currentTableItem = currentM;

        TextView id = (TextView)listItem.findViewById(R.id.id);
        id.setText(" Table #" + String.valueOf(currentM.getId()));

        TextView seats = (TextView) listItem.findViewById(R.id.seats);
        seats.setText(String.valueOf(currentM.getSeats()) + " seats.");

        TextView isOccupied = (TextView) listItem.findViewById(R.id.isOccupied);
        if(currentM.isOccupied())
        {
            isOccupied.setText("Occupied.");
            layout.setBackgroundColor(Color.parseColor("#a85247"));
        }
        else
        {
            isOccupied.setText("Empty.");
            layout.setBackgroundColor(Color.parseColor("#469482"));
        }
        TextView isReserved = (TextView) listItem.findViewById(R.id.isReserved);
        if(currentM.isReserved())
        {
            isReserved.setText("Reserved.");
        }
        else
        {
            isReserved.setText("Not reserved.");
        }

        TextView totalPrice = (TextView) listItem.findViewById(R.id.total);
        totalPrice.setText("Total: " + String.valueOf(currentM.getTotalPrice()));

        View finalListItem = listItem;

        listItem.setOnClickListener(this);
        return listItem;
    }

    @SuppressLint("SetTextI18n")
    private void openDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.alert_dialog_style);
        // Set Custom Title
        TextView title = new TextView(getContext());
        // Title Properties
        title.setText("Modify Table State");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        /*
        // Set Message
        TextView msg = new TextView(getContext());
        // Message Properties
        //msg.setText("Table: " + tableItem.getId()...);
        msg.setText(currentTableItem.getId().toString());
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
        */

        String[] options = {"Occupied", "Reserved"};
        boolean[] checkedItems = {false, false};
        alertDialog.setMultiChoiceItems(options, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
            }
        });

        // Set Button
        // you can more buttons
        alertDialog.setPositiveButton("SAVE CHANGES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
                currentTableItem.setOccupied(checkedItems[0]);
                currentTableItem.setReserved(checkedItems[1]);
                /*
                firebaseFirestore.collection("tables").document().update({
                        "isOccupied":checkedItems[0],
                        "isReserved": checkedItems[1]
                });
                */

                alertDialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        //new Dialog(getContext());
        alertDialog.create().show();

        // Set Properties for OK Button
        /*
        final Button okBT = alertDialog.(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.RED);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.GRAY);
        cancelBT.setLayoutParams(negBtnLP);

         */
    }
}
