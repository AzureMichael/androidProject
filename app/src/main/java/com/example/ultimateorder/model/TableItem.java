package com.example.ultimateorder.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.PropertyName;

public class TableItem {
    private Long id;
    private boolean isOccupied;
    private boolean isReserved;
    private Integer seats;
    private float totalPrice;
    private com.google.firebase.firestore.DocumentReference Waiter;

    public TableItem(Long id, boolean isOccupied, boolean isReserved, Integer seats, float totalPrice, DocumentReference DocumentReference) {
        this.id = id;
        this.isOccupied = isOccupied;
        this.isReserved = isReserved;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.Waiter = DocumentReference;
    }

    public TableItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PropertyName("isOccupied")
    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @PropertyName("isReserved")
    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DocumentReference getWaiter() {
        return Waiter;
    }

    public void setWaiter(DocumentReference DocumentReference) {
        this.Waiter = DocumentReference;
    }
}
