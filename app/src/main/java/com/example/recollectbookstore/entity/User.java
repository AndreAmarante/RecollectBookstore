package com.example.recollectbookstore.entity;

public class User {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String municipality;
    private String district;

    public User(Long id, String name, String email, String phone, String municipality, String district ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.municipality = municipality;
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
