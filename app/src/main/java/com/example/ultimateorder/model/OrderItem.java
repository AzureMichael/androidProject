package com.example.ultimateorder.model;

import com.google.firebase.firestore.DocumentReference;

public class OrderItem {
    private Integer id;
    private DocumentReference table;
    private float price;
    private boolean confirmed;

    public OrderItem(Integer id, DocumentReference table, float price, boolean confirmed) {
        this.id = id;
        this.table = table;
        this.price = price;
        this.confirmed = confirmed;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
