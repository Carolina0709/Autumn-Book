/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.services;

import com.egg.library.entities.Image;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.repositories.PublisherRepository;
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
public class PublisherService {
    
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void createPublisher(String name, String message, MultipartFile file,User user) throws MyException {
        
        
        validatePublisher(name, message, file);
        
        Publisher publisher = new Publisher();
        
        publisher.setName(name);
        publisher.setMessage(message);
        publisher.setSales(0);
        publisher.setUser(user);
        
        Image image = imageService.SaveImage(file);
        publisher.setImage(image);
        
        publisherRepository.save(publisher);
    
    }
    
    @Transactional
    public void editPublisher(String idPublisher, String name, String message, MultipartFile file) throws MyException {
       
        
        validatePublisher(name, message, file);
        
        Optional<Publisher> answer = publisherRepository.findById(idPublisher);
        
        if (answer.isPresent()) {
            Publisher publisher = answer.get();

            publisher.setName(name);
            publisher.setMessage(message);
            publisher.setSales(0);

            String idImage = null;
            
            if(publisher.getImage() != null){
                idImage = publisher.getImage().getId();
            }
            
            Image image = imageService.updateImage(file, idImage);
            publisher.setImage(image);

            publisherRepository.save(publisher);
        }  
    }
    
    @Transactional
    public void editPublisherWithoutImage(String idPublisher, String name, String message) throws MyException {
       
        
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(message == null || message.isEmpty()){
            throw new MyException("Message can not be empty");
        }
        
        Optional<Publisher> answer = publisherRepository.findById(idPublisher);
        
        if (answer.isPresent()) {
            Publisher publisher = answer.get();

            publisher.setName(name);
            publisher.setMessage(message);
            publisher.setSales(0);

            publisherRepository.save(publisher);
        }
       
    
    }
    
    @Transactional
    public void buyBook(String idPublisher) throws MyException {
       
        
        Optional<Publisher> answer = publisherRepository.findById(idPublisher);
      
        if (answer.isPresent()) {
            Publisher publisher = answer.get();

            publisher.setSales(publisher.getSales()+1);

            publisherRepository.save(publisher);
        }
       
    
    }
    
    @Transactional()
    public Publisher getPublisher(String id) {
        return publisherRepository.getOne(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
    
    public void validatePublisher(String name, String message, MultipartFile file) throws MyException{
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
