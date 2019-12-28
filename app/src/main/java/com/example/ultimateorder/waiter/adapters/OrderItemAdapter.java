package com.example.ultimateorder.waiter.adapters;

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
import com.example.ultimateorder.waiter.ui.orders.OrdersViewModel;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.model.OrderItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> implements View.OnClickListener {
    private ArrayList<OrderItem> OrderItems;
    private Context mContext;
    private OrderItem currentOrderItem;
    OrdersViewModel orderViewModel;

    public OrderItemAdapter(ArrayList<OrderItem> data, Context context, OrdersViewModel orderViewModel) {
        super(context, R.layout.orders_item_layout, data);
        this.OrderItems = data;
        this.mContext = context;
        this.orderViewModel = orderViewModel;
    }

    @Override
    public void onClick(View v) {
        openDialog(currentOrderItem, this.orderViewModel, v);
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

    @SuppressLint("SetTextI18n")
    public void openDialog(OrderItem orderItem, OrdersViewModel orderViewModel, View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        // Set Custom Title
        TextView title = new TextView(getContext());
        // Title Properties
        title.setText("Custom Dialog Box");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(getContext());
        // Message Properties
        //msg.setText("Order: " + orderItem.getId() + "\n" + "Total Price: " + orderItem.getPrice() + "\n");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setText("");
        String s = "order" + ((TextView) view.findViewById(R.id.id))
                .getText()
                .subSequence(7, 16).toString();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("orders").document(s).collection("mealItems").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                queryDocumentSnapshots.forEach(queryDocumentSnapshot -> {
                    DocumentReference reference = queryDocumentSnapshot.getDocumentReference("meal");
                    reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            MealItem mealItem = documentSnapshot.toObject(MealItem.class);
                            msg.setText(msg.getText() + "\n" + mealItem.getName());
                        }
                    });
                });
            }
        });
        //msg.setText("Order: " + orderItem.getId() + "\n" + "Total Price: " + orderItem.getPrice() + "\n");
        alertDialog.setView(msg);

        //ToggleButton finished = new ToggleButton(getContext());
        //alertDialog.setView(finished);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
                final boolean[] booleans = {false};
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                String s = "order" + ((TextView) view.findViewById(R.id.id))
                        .getText()
                        .subSequence(7, 16).toString();
                CollectionReference collectionReference = firebaseFirestore.collection("orders").document(s).collection("mealItems");
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        queryDocumentSnapshots.forEach(queryDocumentSnapshot -> {
                            DocumentReference reference = queryDocumentSnapshot.getReference();
                            reference.delete();
                        });
                        firebaseFirestore
                                .collection("orders")
                                .document("order" + ((TextView) view.findViewById(R.id.id))
                                        .getText()
                                        .subSequence(7, 16)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AtomicReference<OrderItem> orderItem = new AtomicReference<>(new OrderItem());
                                orderItem.get().setId((Integer.valueOf(((TextView) view.findViewById(R.id.id))
                                        .getText()
                                        .subSequence(7, 16).toString())));
                                orderItem.get().setPrice(Float.valueOf(((TextView) view.findViewById(R.id.price))
                                        .getText()
                                        .subSequence(7, 12).toString()));
                                List<OrderItem> orderItemList = orderViewModel.getmOrderItems()
                                        .getValue();
                                AtomicBoolean ok = new AtomicBoolean(false);
                                orderItemList.forEach(orderItem1 -> {
                                    int id1 = orderItem.get().getId();
                                    int id2 = orderItem1.getId();
                                    if (id1 == id2) {
                                        ok.set(true);
                                        orderItem.set(orderItem1);
                                    }
                                });
                                if (ok.get())
                                    orderItemList.remove(orderItem.get());
                                orderViewModel.getmOrderItems().setValue(orderItemList);
                            }
                        });

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
        });

        alertDialog.show();
    }
}