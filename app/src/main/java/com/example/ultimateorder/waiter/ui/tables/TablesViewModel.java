package com.example.ultimateorder.waiter.ui.tables;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.OrderItem;
import com.example.ultimateorder.model.TableItem;

import java.util.List;

public class TablesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<TableItem>> mTableItems;

    public TablesViewModel() {
        mTableItems = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("Tables");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<TableItem>> getmTableItems() {
        return mTableItems;
    }

    public void setmTableItems(List<TableItem> mTableItems) {
        this.mTableItems.setValue(mTableItems);
    }
}