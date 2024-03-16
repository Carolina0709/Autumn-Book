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
public class Book {
    
     //Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(name = "name")
    private String name;
    
    
    @Column(name = "foreword")
    private String foreword;
    
    @Column(name = "price")
    private String price;
    
    @OneToOne
    private Image image;
    
    @Column(name = "sales")
    private int sales;
    
    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;
    
    @ManyToOne
    @JoinColumn(name = "publisher")
    private Publisher publisher;
    
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    
      
    //Constructors

    public Book() {
    }

    public Book(String name, String foreword, String price, String image,  int sales) {
        this.name = name;
        this.foreword = foreword;
        this.price = price;
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

    public String getForeword() {
        return foreword;
    }

    public void setForeword(String foreword) {
        this.foreword = foreword;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
