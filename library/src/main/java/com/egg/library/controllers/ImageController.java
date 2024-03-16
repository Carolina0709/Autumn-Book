/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.services.AuthorService;
import com.egg.library.services.BookService;
import com.egg.library.services.PublisherService;
import com.egg.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author caro9
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    AuthorService authorService;
    
    @Autowired
    BookService bookService;
    
    @Autowired
    PublisherService publisherService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/authors/{id}")
    public ResponseEntity<byte[]> authorImage (@PathVariable String id){
       Author author = authorService.getAuthor(id);
       
       byte[] image = author.getImage().getContent();
       
       HttpHeaders headers = new HttpHeaders();
       
       return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @GetMapping("/books/{id}")
    public ResponseEntity<byte[]> bookImage (@PathVariable String id){
       Book book = bookService.getBook(id);
       
       byte[] image = book.getImage().getContent();
       
       HttpHeaders headers = new HttpHeaders();
       
       return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @GetMapping("/publishers/{id}")
    public ResponseEntity<byte[]> publisherImage (@PathVariable String id){
       Publisher publisher = publisherService.getPublisher(id);
       
       byte[] image = publisher.getImage().getContent();
       
       HttpHeaders headers = new HttpHeaders();
       
       return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<byte[]> userImage (@PathVariable String id){
       User user = userService.getUser(id);
       
       byte[] image = user.getImage().getContent();
       
       HttpHeaders headers = new HttpHeaders();
       
       return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
