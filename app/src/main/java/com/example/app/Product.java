package com.example.app;

import java.util.List;

public class Product {
    private String name;
    private String price;
    private List<Integer> size;
    private int drawableId;
    private String description;
    private String selectedSize;
    private int quantity;

    public Product(String name, String price, List<Integer> size, int drawableId, String description) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.drawableId = drawableId;
        this.description = description;
        this.quantity = 1;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public List<Integer> getSize() { return size; }
    public int getDrawableId() { return drawableId; }
    public String getDescription() { return description; }
    public String getSelectedSize() { return selectedSize; }
    public void setSelectedSize(String selectedSize) { this.selectedSize = selectedSize; } // Setter for selected size
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
