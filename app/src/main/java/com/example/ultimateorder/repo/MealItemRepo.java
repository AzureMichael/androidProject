package com.example.ultimateorder.repo;

import com.example.ultimateorder.model.MealItem;

import java.util.List;

public class MealItemRepo {

    private List<MealItem> inMemory;

    public MealItemRepo() {
    }

    public List<MealItem> getInMemory() {
        return inMemory;
    }

    public void setInMemory(List<MealItem> inMemory) {
        this.inMemory = inMemory;
    }

    public void addMealItem(MealItem mealItem){
        this.inMemory.add(mealItem);
    }
}
