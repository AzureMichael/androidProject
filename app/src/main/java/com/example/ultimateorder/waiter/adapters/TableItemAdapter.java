package com.example.ultimateorder.waiter.adapters;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ultimateorder.model.TableItem;
import com.example.ultimateorder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TableItemAdapter extends ArrayAdapter<TableItem> implements View.OnClickListener {
    private ArrayList<TableItem> TableItems;
    Context mContext;
    TableItem currentTableItem;
    private FirebaseFirestore firebaseFirestore;
    private boolean[] checkedItems = {false, false};
    private View listItem;

    public TableItemAdapter(ArrayList<TableItem> data, Context context) {
        super(context, R.layout.table_item_layout, data);
        this.TableItems = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) { openDialog(v); }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listItem = convertView;
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
    private void openDialog(View view) {
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

        String[] options = {"Occupied", "Reserved"};
        String table = "table" + ((TextView)view.findViewById(R.id.id)).getText().subSequence(8,((TextView)view.findViewById(R.id.id)).getText().length()).toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("tables")
                .document(table)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        TableItem table = documentSnapshot.toObject(TableItem.class);
                        if(table != null) {
                            checkedItems[0] = table.isOccupied();
                            checkedItems[1] = table.isReserved();
                        }

                        alertDialog.setMultiChoiceItems(options, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // user checked or unchecked a box
                            }
                        });

                        // Set Button
                        alertDialog.setPositiveButton("SAVE CHANGES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Action on Button
                                table.setOccupied(checkedItems[0]);
                                table.setReserved(checkedItems[1]);

                                firebaseFirestore.collection("tables")
                                        .document("table" + table.getId())
                                        .set(table)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("SET TableItemAdapter", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("SET TableItemAdapter", "Error writing document", e);
                                            }
                                        });
                            }
                        });

                        alertDialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Action on Button
                            }
                        });

                        alertDialog.create().show();
                    }
                });
    }
}