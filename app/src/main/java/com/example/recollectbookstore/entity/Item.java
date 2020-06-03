package com.example.recollectbookstore.entity;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Item {

    private Long id;
    private ArrayList<String> images;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;
    private String creationDate;
    private ArrayList<Comment> comments;
    private User owner;

    public Item(Long id, String name, int quantity, double price, String description, ArrayList<String> images,
                String creationDate, String category){
        this.id = id;
        this.images = images;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.creationDate = creationDate;
    }

    //Used in ItemDetail
    public Item(Long id, String name, int quantity, double price, String description, ArrayList<String> images,
                String creationDate, String category, ArrayList<Comment> comments, User owner ){
        this.id = id;
        this.images = images;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.comments = comments;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstImage() {
        return images.get(0);
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
