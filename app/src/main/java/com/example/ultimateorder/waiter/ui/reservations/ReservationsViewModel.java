package com.example.ultimateorder.waiter.ui.reservations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.ReservationItem;

import java.util.List;

public class ReservationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<ReservationItem>> mOrderItems;

    public ReservationsViewModel() {
        this.mOrderItems = new MutableLiveData<List<ReservationItem>>();
        mText = new MutableLiveData<>();
        mText.setValue("Reservations");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<ReservationItem>> getmOrderItems() {
        return mOrderItems;
    }

    public void setmOrderItems(List<ReservationItem> mOrderItems) {
        this.mOrderItems.setValue(mOrderItems);
    }
}