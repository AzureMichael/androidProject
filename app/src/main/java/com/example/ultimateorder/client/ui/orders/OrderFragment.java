package com.example.ultimateorder.client.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.client.adapter.OrderItemAdapter;
import com.example.ultimateorder.model.OrderItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private FirebaseFirestore firebaseFirestore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<OrderItem> mealItems;
                mealItems = queryDocumentSnapshots.toObjects(OrderItem.class);
                orderViewModel.setMutableLiveData(mealItems);
            }
        });
        firebaseFirestore.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<OrderItem> orderItems = new ArrayList<>();
                if(queryDocumentSnapshots != null)
                    orderItems = queryDocumentSnapshots.toObjects(OrderItem.class);
                List<OrderItem> newOrderItems = new ArrayList<>();
                List<OrderItem> finalOrderItems = orderItems;
                if(orderViewModel.getMutableLiveData().getValue()!=null) {
                   finalOrderItems.forEach(orderItem -> {
                        if (!orderViewModel.getMutableLiveData().getValue().contains(orderItem)) {
                            newOrderItems.add(orderItem);
                        }
                    });
                }
                orderViewModel.setMutableLiveData(newOrderItems);
            }
        });

        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orders_client, container, false);
        final ListView view = root.findViewById(R.id.list);
        final TextView view1 = root.findViewById(R.id.textView);
        orderViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                view1.setText(s);
            }
        });
        orderViewModel.getMutableLiveData().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> mealItems) {
                OrderItemAdapter adapter = new OrderItemAdapter((ArrayList<OrderItem>) mealItems, getContext(), orderViewModel);
                view.setAdapter(adapter);
            }
        });

        return root;
    }

}
