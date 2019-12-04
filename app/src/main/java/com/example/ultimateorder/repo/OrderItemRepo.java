package com.example.ultimateorder.repo;


import com.example.ultimateorder.model.OrderItem;

import java.util.List;

public class OrderItemRepo {
    private List<OrderItem> inMemory;

    public OrderItemRepo() {
    }

    public List<OrderItem> getInMemory() {
        return inMemory;
    }

    public void setInMemory(List<OrderItem> inMemory) {
        this.inMemory = inMemory;
    }

    public void addMealItem(OrderItem mealItem){
        this.inMemory.add(mealItem);
    }

}
