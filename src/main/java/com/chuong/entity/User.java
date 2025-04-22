package com.chuong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
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


}
