package com.example.ultimateorder.waiter.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ultimateorder.model.OrderItem;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<OrderItem>> mOrderItems;

    public HomeViewModel() {
        mOrderItems = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("This is orders fragment");
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