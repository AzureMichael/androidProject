package com.example.ultimateorder.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ultimateorder.R;
import com.example.ultimateorder.model.ReservationItem;

import java.util.ArrayList;

public class ReservationItemAdapter extends ArrayAdapter<ReservationItem> implements View.OnClickListener {
    private ArrayList<ReservationItem> reservationItems;
    Context mContext;

    public ReservationItemAdapter(ArrayList<ReservationItem> data, Context context) {
        super(context, R.layout.orders_item_layout, data);
        this.reservationItems = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
    }

    private int lastPosition = -1;

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.reservations_item_layout, parent, false);

        ReservationItem currentM = reservationItems.get(position);

        TextView id = listItem.findViewById(R.id.clientName);

        id.setText("Name: " + currentM.getClientName());

        TextView totalPrice = listItem.findViewById(R.id.reservationDate);

        totalPrice.setText("Date: " + currentM.getReservationDate());

        TextView seats = listItem.findViewById(R.id.seatsReserved);

        seats.setText("Nr. of Seats Reserved: " + currentM.getSeatsReserved());

        return listItem;
    }
}

