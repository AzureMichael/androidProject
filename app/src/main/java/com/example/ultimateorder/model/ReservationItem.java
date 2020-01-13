package com.example.ultimateorder.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class ReservationItem {
    private Integer id;
    private String clientName;
    private boolean isConfirmed;
    private Date reservationDate;
    private Integer seatsReserved;
    private DocumentReference table;
    private String userID;

    public ReservationItem() {
    }

    public ReservationItem(String clientName, boolean isConfirmed, java.util.Date reservationDate, Integer seatsReserved, DocumentReference table) {
        this.clientName = clientName;
        this.isConfirmed = isConfirmed;
        this.reservationDate = reservationDate;
        this.seatsReserved = seatsReserved;
        this.table = table;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.isConfirmed = confirmed;
    }

    public java.util.Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(java.util.Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getSeatsReserved() {
        return seatsReserved;
    }

    public void setSeatsReserved(Integer seatsReserved) {
        this.seatsReserved = seatsReserved;
    }

    public DocumentReference getTable() {
        return table;
    }

    public void setTable(DocumentReference table) {
        this.table = table;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
