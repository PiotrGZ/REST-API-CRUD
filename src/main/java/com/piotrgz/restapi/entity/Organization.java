package com.piotrgz.restapi.entity;



import javax.persistence.*;

@Entity
public class Organization {
    @Id
    private String name;

    public Organization() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}