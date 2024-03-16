/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author caro9
 */

@Entity
public class Author {
    
    //Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "sales")
    private int sales;
    
    @OneToOne
    private Image image;
    
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    
    //Constructors

    public Author() {
    }

    public Author(String name, String message, String image, int sales) {
        this.name = name;
        this.message = message;
        this.sales = sales;
    }
    
    
    //Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
