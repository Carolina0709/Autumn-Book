/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.services;

import com.egg.library.entities.Author;
import com.egg.library.entities.Image;
import com.egg.library.entities.User;
import com.egg.library.repositories.AuthorRepository;
import exceptions.MyException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void createAuthor(String name, String message, MultipartFile file, User user) throws MyException {
        
        
        validateAuthor(name, message, file);
        
        Author author = new Author();
        
        author.setName(name);
        author.setMessage(message);
        author.setSales(0);
        author.setUser(user);
        
        Image image = imageService.SaveImage(file);
        author.setImage(image);
        
        authorRepository.save(author);
    
    }
    
    @Transactional
    public void editAuthor(String idAuthor, String name, String message, MultipartFile file) throws MyException {
       
        
        validateAuthor(name, message, file);
        
        Optional<Author> answer = authorRepository.findById(idAuthor);
        
        if (answer.isPresent()) {
            Author author = answer.get();

            author.setName(name);
            author.setMessage(message);
            author.setSales(0);

            String idImage = null;
            
            if(author.getImage() != null){
                idImage = author.getImage().getId();
            }
            
            Image image = imageService.updateImage(file, idImage);
            author.setImage(image);

            authorRepository.save(author);
        }
       
    
    }
    
    
    @Transactional
    public void editAuthorWithoutImage(String idAuthor, String name, String message) throws MyException {
       
        
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(message == null || message.isEmpty()){
            throw new MyException("Message can not be empty");
        }
        
        Optional<Author> answer = authorRepository.findById(idAuthor);
        
        if (answer.isPresent()) {
            Author author = answer.get();

            author.setName(name);
            author.setMessage(message);
            author.setSales(0);

            authorRepository.save(author);
        }
       
    
    }
    
    @Transactional
    public void buyBook(String idAuthor) throws MyException {
       
        
        Optional<Author> answer = authorRepository.findById(idAuthor);
      
        if (answer.isPresent()) {
            Author author = answer.get();

            author.setSales(author.getSales()+1);

            authorRepository.save(author);
        }
       
    
    }
    
    @Transactional()
    public Author getAuthor(String id) {
        return authorRepository.getOne(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public void validateAuthor(String name, String message, MultipartFile file) throws MyException{
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(message == null || message.isEmpty()){
            throw new MyException("Message can not be empty");
        }
        
        if(file == null || file.isEmpty()){
            throw new MyException("Image can not be empty");
        }      
        
    } 

    
}
