package com.example.ultimateorder.waiter.ui.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.adapter.OrderItemAdapter;
import com.example.ultimateorder.model.OrderItem;
import com.example.ultimateorder.repo.OrderItemRepo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    private static final String TAG = "Orders Fragment";

    private OrderItemRepo repo = new OrderItemRepo();
    private OrdersViewModel ordersViewModel;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.addAll(queryDocumentSnapshots.toObjects(OrderItem.class));
                ordersViewModel.setmOrderItems(orderItems);
            }
        });

        View root = inflater.inflate(R.layout.order_fragment_waiter, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        ordersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ListView view = root.findViewById(R.id.list);
        ordersViewModel.getmOrderItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                OrderItemAdapter adapter = new OrderItemAdapter((ArrayList<OrderItem>) orderItems,getContext());
                view.setAdapter(adapter);
            }
        });

        // when another order is added
        firebaseFirestore.collection("orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED) {
                                Toast.makeText(getContext(), "New order: " + dc.getDocument().getData(), Toast.LENGTH_LONG).show();
                                Log.d(TAG, "New order: " + dc.getDocument().getData());
                            }
                        }
                    }
                });
        return root;
    }
}