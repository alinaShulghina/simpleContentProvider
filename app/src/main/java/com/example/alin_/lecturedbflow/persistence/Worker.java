package com.example.alin_.lecturedbflow.persistence;



/**
 * Created by alin- on 15.11.2017.
 */
public class Worker {

    private int id;

    private String name;

    private Company company;

    private long dateOfBirth;

    public Worker(String name,long dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Worker(int id, String name, Company company, long dateOfBirth) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.dateOfBirth = dateOfBirth;
    }
    public Worker(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
