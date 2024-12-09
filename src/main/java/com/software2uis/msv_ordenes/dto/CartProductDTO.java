package com.software2uis.msv_ordenes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartProductDTO {
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    private double price;
    private int quantity;

    public CartProductDTO() {}

    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
