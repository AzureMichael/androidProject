package com.example.ultimateorder.kitchen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.ultimateorder.R;
import com.example.ultimateorder.kitchen.adapter.OrderItemAdapter;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.model.OrderItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class KitchenActivity extends AppCompatActivity {
    private static final String TAG = "KITCHEN";
    private OrderViewModelKitchen orderViewModelKitchen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        orderViewModelKitchen = new OrderViewModelKitchen();
        final ListView view = this.findViewById(R.id.kitchen_list);
        final TextView text = this.findViewById(R.id.kitchen_orders_text);
        text.setText("Orders");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<OrderItem> orderItems = queryDocumentSnapshots.toObjects(OrderItem.class);
                orderViewModelKitchen.setMutableLiveData(orderItems);
                Log.d("UPDATE", "Data received" + orderItems.toString());
                firebaseFirestore.collection("orders")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(TAG, "listen:error", e);
                                    return;
                                }
                                assert queryDocumentSnapshots != null;
                                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                                    OrderItem tableItem = dc.getDocument().toObject(OrderItem.class);
                                    List<OrderItem> tableItems = orderViewModelKitchen.getMutableLiveData().getValue();
                                    assert tableItems != null;
                                    if (!tableItems.contains(tableItem))
                                        if (dc.getType() == DocumentChange.Type.ADDED) {
                                            Toast.makeText(KitchenActivity.this, "New order: " + dc.getDocument().getData(), Toast.LENGTH_LONG).show();
                                            tableItems.add(tableItem);
                                            orderViewModelKitchen.getMutableLiveData().setValue(tableItems);
                                            openDialog(tableItem);
                                            Log.d(TAG, "New order: " + dc.getDocument().getData());
                                        }
                                }
                            }
                        });
            }
        });
        orderViewModelKitchen.getMutableLiveData().observe(KitchenActivity.this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                OrderItemAdapter adapter = new OrderItemAdapter(orderItems, KitchenActivity.this,orderViewModelKitchen);
                view.setAdapter(adapter);
            }
        });


    }
    public void openDialog(OrderItem item) {
        AlertDialog alertDialog = new AlertDialog.Builder(KitchenActivity.this).create();

        // Set Custom Title
        TextView title = new TextView(KitchenActivity.this);
        // Title Properties
        title.setText("Custom Dialog Box");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(KitchenActivity.this);
        // Message Properties
        //msg.setText("Order: " + orderItem.getId() + "\n" + "Total Price: " + orderItem.getPrice() + "\n");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        msg.setText("");
        String s = "order" + item.getId();
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
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONFIRM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                item.setConfirmed(true);
                alertDialog.cancel();
                firebaseFirestore.collection("orders").document("order"+item.getId()).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(KitchenActivity.this, "Status of order updated: " + item.isConfirmed(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

}
