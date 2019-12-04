package com.example.ultimateorder.model;

class Waiter {
    private Long id;
    private String type;
    private String email;

    public Waiter() {
    }

    public Waiter(Long id, String type, String email) {
        this.id = id;
        this.type = type;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
