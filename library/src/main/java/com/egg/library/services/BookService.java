/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.services;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.Image;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.repositories.BookRepository;
import exceptions.MyException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
   
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void createBook(String name, String foreword, String price, Author id_author, Publisher id_publisher, MultipartFile file, User user) throws MyException {
        
        
        validateBook(name, foreword, price, id_author, id_publisher, file);
        
        Book book = new Book();
        
        book.setName(name);
        book.setForeword(foreword);
        book.setPrice(price);
        book.setAuthor(id_author);
        book.setPublisher(id_publisher);
        book.setSales(0);
        book.setUser(user);
        
        Image image = imageService.SaveImage(file);
        book.setImage(image);
        
        bookRepository.save(book);
    
    }
    
    
    @Transactional
    public void editBook(String idBook, String name, String foreword, String price, Author id_author, Publisher id_publisher, MultipartFile file) throws MyException {
       
        
        validateBook(name, foreword, price, id_author, id_publisher, file);
        
        Optional<Book> answer = bookRepository.findById(idBook);
        
        if (answer.isPresent()) {
            Book book = answer.get();

            book.setName(name);
            book.setForeword(foreword);
            book.setPrice(price);
            book.setAuthor(id_author);
            book.setPublisher(id_publisher);
            book.setSales(0);

            String idImage = null;
            
            if(book.getImage() != null){
                idImage = book.getImage().getId();
            }
            
            Image image = imageService.updateImage(file, idImage);
            book.setImage(image);

            bookRepository.save(book);
        }
       
    
    }
    
    @Transactional
    public void editBookWithoutImage(String idBook, String name, String foreword, String price, Author id_author, Publisher id_publisher) throws MyException {
       
        
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(foreword == null || foreword.isEmpty()){
            throw new MyException("Foreword can not be empty");
        }
        
        if(price == null || price.isEmpty()){
            throw new MyException("Price can not be empty");
        }
        
        if(id_author == null){
            throw new MyException("Author can not be empty");
        }
        if(id_publisher == null){
            throw new MyException("Publisher can not be empty");
        }
        
        Optional<Book> answer = bookRepository.findById(idBook);
        
        if (answer.isPresent()) {
            Book book = answer.get();

            book.setName(name);
            book.setForeword(foreword);
            book.setPrice(price);
            book.setAuthor(id_author);
            book.setPublisher(id_publisher);
            book.setSales(0);

            bookRepository.save(book);
        }  
    }
    
    @Transactional
    public void buyBook(String idBook) throws MyException {
       
        
        Optional<Book> answer = bookRepository.findById(idBook);
      
        if (answer.isPresent()) {
            Book book = answer.get();

            book.setSales(book.getSales()+1);

            bookRepository.save(book);
        }
       
    
    }
    
    @Transactional()
    public Book getBook(String id) {
        return bookRepository.getOne(id);
    }
    
    public void validateBook(String name, String foreword, String price, Author id_author, Publisher id_publisher, MultipartFile file) throws MyException{
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(foreword == null || foreword.isEmpty()){
            throw new MyException("Foreword can not be empty");
        }
        if(price == null || price.isEmpty()){
            throw new MyException("Price can not be empty");
        }
        if(id_author == null){
            throw new MyException("Author can not be empty");
        }
        if(id_publisher == null){
            throw new MyException("Publisher can not be empty");
        }
        if(file == null || file.isEmpty()){
            throw new MyException("Image can not be empty");
        }      
        
    } 
    
}
