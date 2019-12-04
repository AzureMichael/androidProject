package com.example.ultimateorder.model;

import com.google.firebase.firestore.DocumentReference;

public class OrderItem {
    private Integer id;
    private DocumentReference tableRef;
    private float price;

    public OrderItem(Integer id, DocumentReference table, float price) {
        this.id = id;
        this.tableRef = table;
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

    public DocumentReference getTableRef() {
        return tableRef;
    }

    public void setTableRef(DocumentReference tableRef) {
        this.tableRef = tableRef;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
