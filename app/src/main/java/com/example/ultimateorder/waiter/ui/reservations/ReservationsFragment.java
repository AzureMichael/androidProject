package com.example.ultimateorder.waiter.ui.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.client.adapter.ReservationItemAdapter;
import com.example.ultimateorder.model.ReservationItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private ReservationsViewModel reservationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservationsViewModel =
                ViewModelProviders.of(this).get(ReservationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseFirestore.collection("reservations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ReservationItem> reservationItems = queryDocumentSnapshots.toObjects(ReservationItem.class);
                reservationsViewModel.setmOrderItems(reservationItems);
                final ListView view = root.findViewById(R.id.list);
                reservationsViewModel.getmOrderItems().observe(ReservationsFragment.this, new Observer<List<ReservationItem>>() {
                    @Override
                    public void onChanged(List<ReservationItem> reservationItems) {
                        ReservationItemAdapter reservationItemAdapter = new ReservationItemAdapter((ArrayList<ReservationItem>) reservationItems,getContext());
                        view.setAdapter(reservationItemAdapter);
                    }
                });

            }
        });
        final TextView textView = root.findViewById(R.id.text_reservations);
        reservationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}