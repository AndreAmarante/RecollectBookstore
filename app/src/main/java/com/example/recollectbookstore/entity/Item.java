package com.example.recollectbookstore.entity;

import java.sql.Timestamp;
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
    private Date creationDate;

    public Item(Long id, String name, int quantity, double price, String description, ArrayList<String> images,
            Date creationDate, String category){
        this.id = id;
        this.images = images;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.creationDate = creationDate;
    }

}
