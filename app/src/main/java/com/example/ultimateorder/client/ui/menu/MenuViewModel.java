package com.example.ultimateorder.client.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimateorder.model.MealItem;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<MealItem>> mMealItems;
    private MutableLiveData<List<MealItem>> newOrderMeals;
    private MutableLiveData<Boolean> isNewOrderAvailable;

    public MenuViewModel() {
        isNewOrderAvailable = new MutableLiveData<>();
        newOrderMeals = new MutableLiveData<>();
        mMealItems = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("This is the Restaurant Menu");
        newOrderMeals.setValue(new ArrayList<>());
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

    public MutableLiveData<Boolean> getIsNewOrderAvailable() {
        return isNewOrderAvailable;
    }

    public void setIsNewOrderAvailable(MutableLiveData<Boolean> isNewOrderAvailable) {
        this.isNewOrderAvailable = isNewOrderAvailable;
    }

    public MutableLiveData<List<MealItem>> getNewOrderMeals() {
        return newOrderMeals;
    }

    public void setNewOrderMeals(MutableLiveData<List<MealItem>> newOrderMeals) {
        this.newOrderMeals = newOrderMeals;
    }
}