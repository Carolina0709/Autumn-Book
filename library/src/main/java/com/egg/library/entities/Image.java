/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author caro9
 */
@Entity
public class Image {
       //Atributos
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "mime")
    private String mime;
    
    @Column(name = "content", columnDefinition = "LONGBLOB")
    @Lob 
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
    
    //Constructor
    public Image() {
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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    
}
