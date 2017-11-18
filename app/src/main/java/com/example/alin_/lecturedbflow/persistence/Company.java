package com.example.alin_.lecturedbflow.persistence;


/**
 * Created by alin- on 15.11.2017.
 */

class Company {

    private int id;

    private String name;

    private String location;

    private String email;

    public Company(int id, String name, String location, String email) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
