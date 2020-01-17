package com.example.ultimateorder.kitchen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.OrderItem;

import java.util.List;

public class OrderViewModelKitchen extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<OrderItem>> mutableLiveData;

    public OrderViewModelKitchen() {
        mutableLiveData = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("These are your orders");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<OrderItem>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<OrderItem> mutableLiveData) {
        this.mutableLiveData.setValue(mutableLiveData);
    }
}