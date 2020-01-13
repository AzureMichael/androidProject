package com.example.ultimateorder.client.ui.reservations;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.client.adapter.ReservationItemAdapter;
import com.example.ultimateorder.client.ui.orders.OrderViewModel;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.model.OrderItem;
import com.example.ultimateorder.model.ReservationItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ReservationsFragment extends Fragment {

    private ReservationsViewModel reservationsViewModel;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseFirestore.collection("reservations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ReservationItem> reservationItems = queryDocumentSnapshots.toObjects(ReservationItem.class);
                reservationsViewModel.setReservationItemMutableLiveData(reservationItems);
            }
        });
        reservationsViewModel =
                ViewModelProviders.of(this).get(ReservationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reservations_client, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        reservationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final ListView view = root.findViewById(R.id.list);
        reservationsViewModel.getReservationItemMutableLiveData().observe(this, new Observer<List<ReservationItem>>() {
            @Override
            public void onChanged(List<ReservationItem> reservationItems) {
                ReservationItemAdapter reservationItemAdapter = new ReservationItemAdapter((ArrayList<ReservationItem>) reservationItems,getContext());
                view.setAdapter(reservationItemAdapter);
            }
        });
        final Button addReservationButton = root.findViewById(R.id.addReservationButton);
        addReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        return root;
    }

    public void openDialog(View view) {
        // custom dialog
        final Dialog dialog = new Dialog(getContext());

        View reservationDialogView = getLayoutInflater().inflate(R.layout.view_reservations_add_dialog,null);
        dialog.setContentView(reservationDialogView);
        dialog.setTitle("Add New Reservation");

        ReservationItem reservationItem = new ReservationItem();

        Button addReservationButton = reservationDialogView.findViewById(R.id.addReservationBtn);
        addReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userID = firebaseAuth.getUid();
                reservationItem.setUserID(userID);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date reservationDate = dateFormat.parse(((EditText)reservationDialogView.findViewById(R.id.date)).getText().toString());
                    reservationItem.setReservationDate(reservationDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String name = ((EditText)reservationDialogView.findViewById(R.id.name)).getText().toString();
                String surname = ((EditText)reservationDialogView.findViewById(R.id.surname)).getText().toString();
                Integer numberOfSeats = Integer.valueOf(((EditText)reservationDialogView
                        .findViewById(R.id.numberOfSeats))
                        .getText()
                        .toString());
                String tableID = "table"+((EditText)reservationDialogView.findViewById(R.id.tableNumber)).getText().toString();
                reservationItem.setClientName(name + " " + surname);
                reservationItem.setSeatsReserved(numberOfSeats);
                firebaseFirestore.collection("tables").document(tableID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        reservationItem.setTable(documentSnapshot.getReference());
                        Random random = new Random();
                        reservationItem.setId(random.nextInt(1000000000));
                        firebaseFirestore.collection("reservations").document("reservation"+reservationItem.getId()).set(reservationItem);
                    }
                });
            }
        });

        Button dialogCloseButton = (Button) reservationDialogView.findViewById(R.id.closeReservationDialog);
        // if button is clicked, close the custom dialog
        dialogCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}