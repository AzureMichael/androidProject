package com.example.ultimateorder.client.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.OrderItem;

import java.util.List;

public class OrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<OrderItem>> mutableLiveData;

    public OrderViewModel() {
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