package com.example.ultimateorder.client.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.MealItem;

import java.util.List;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<MealItem>> mMealItems;

    public MenuViewModel() {
        mMealItems = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("This is the Restaurant Menu");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<MealItem>> getmMealItems() {
        return mMealItems;
    }

    public void setmMealItems(List<MealItem> mMealItems) {
        this.mMealItems.setValue(mMealItems);
    }
}