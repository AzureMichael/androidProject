package com.example.ultimateorder.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;

public class OrderItem {
    private Integer id;
    private DocumentReference table;
    private float price;

    public OrderItem(Integer id, DocumentReference table, float price) {
        this.id = id;
        this.table = table;
        this.price = price;
    }

    public OrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DocumentReference getTable() {
        return table;
    }

    public void setTable(DocumentReference table) {
        this.table = table;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
