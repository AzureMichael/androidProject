package com.example.ultimateorder.waiter.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ultimateorder.model.OrderItem;
import java.util.List;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<OrderItem>> mOrderItems;

    public OrdersViewModel() {
        mOrderItems = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("Orders");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<OrderItem>> getmOrderItems() {
        return mOrderItems;
    }

    public void setmOrderItems(List<OrderItem> mOrderItems) {
        this.mOrderItems.setValue(mOrderItems);
    }
}