package com.example.ultimateorder.model;

public class MealItem {

    public MealItem() {
    }

    private String ingredients;
    private String name;
    private Float price;

    public MealItem(String ingredients, String name, Float price) {
        this.ingredients = ingredients;
        this.name = name;
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
