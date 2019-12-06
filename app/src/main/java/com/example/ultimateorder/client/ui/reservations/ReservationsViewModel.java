package com.example.ultimateorder.client.ui.reservations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.ReservationItem;

import java.util.List;

public class ReservationsViewModel extends ViewModel {
    private MutableLiveData<List<ReservationItem>> reservationItemMutableLiveData;
    private MutableLiveData<String> mText;


    public ReservationsViewModel() {
        mText = new MutableLiveData<>();
        reservationItemMutableLiveData = new MutableLiveData<>();
        mText.setValue("Your Reservations");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<ReservationItem>> getReservationItemMutableLiveData() {
        return reservationItemMutableLiveData;
    }

    public void setReservationItemMutableLiveData(List<ReservationItem> reservationItemMutableLiveData) {
        this.reservationItemMutableLiveData.setValue(reservationItemMutableLiveData);
    }
}