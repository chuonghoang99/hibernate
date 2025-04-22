package com.chuong.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "\"user\"")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private int id;

    private String name;

    private String tech;

    public User(){}

    public User(int id, String name, String tech) {
        this.id = id;
        this.name = name;
        this.tech = tech;
    }

    public User(String name, String tech) {

        this.name = name;
        this.tech = tech;
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

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }
}
