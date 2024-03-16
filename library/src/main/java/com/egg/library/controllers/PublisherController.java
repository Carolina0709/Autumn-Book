/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Book;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.repositories.BookRepository;
import com.egg.library.repositories.PublisherRepository;
import com.egg.library.services.PublisherService;
import com.egg.library.services.UserService;
import exceptions.MyException;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private PublisherService publisherService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/see")
    public String seePublishers(@PageableDefault(size=6) Pageable pageable, Model model){
        Page<Publisher> publishers = publisherRepository.findAllByOrderBySalesDesc(pageable);
        
        if(publishers.isEmpty()){
            model.addAttribute("empty", "No publishers to show");   
        }else{
            model.addAttribute("publishers", publishers);    
        }
        
        return "seePublishers.html";
    }
    
    @GetMapping("/see/{id}")
    public String seePublisher(@PathVariable String id, Model model, @PageableDefault(size = 3) Pageable pageable, Principal principal) {
        Publisher publisher = publisherService.getPublisher(id);
        model.addAttribute("publisher", publisher);

        List<Book> bestBooks = bookRepository.findBestBooksFromPublisher(publisher.getId(), pageable);

        if (bestBooks.isEmpty()) {
            model.addAttribute("empty", "No books to show");
        } else {
            model.addAttribute("books", bestBooks);
        }

        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("user", user);

        return "seePublisher.html";
    }
    
    @GetMapping("/create")
    public String createPublisher(Model model){
        model.addAttribute("publisher", new Publisher());
        return "createPublisher.html";
    }

    @PostMapping("/create")
    public String createPublisher(@ModelAttribute("publisher") Publisher publisher, @RequestParam("file") MultipartFile file, ModelMap model, Principal principal) throws MyException {
        try {
            
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);
            
            publisherService.createPublisher(publisher.getName(), publisher.getMessage(), file, user);
            model.put("success", "Publisher created successfully.");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "createPublisher.html";
        }
        return "createPublisher.html";
    }

    
    @GetMapping("/edit/{id}")
    public String editPublisher(@PathVariable String id, Model model) {
        Publisher publisher = publisherService.getPublisher(id);
        model.addAttribute("publisher", publisher);
        return "EditPublisher.html";
    }
    
    
    
    @PostMapping("/edit/{id}")
    public String editPublisher(@ModelAttribute("publisher") Publisher publisher, @RequestParam MultipartFile file, ModelMap model ){
        try {
            if (!file.isEmpty()) {
                publisherService.editPublisher(publisher.getId(), publisher.getName(), publisher.getMessage(), file);
            } else {
                Publisher existingPublisher = publisherService.getPublisher(publisher.getId());
                publisher.setImage(existingPublisher.getImage());
                publisherService.editPublisherWithoutImage(publisher.getId(), publisher.getName(), publisher.getMessage());
            }
            model.put("success", "Publisher edited successfully");  
            Publisher newPublisher = publisherService.getPublisher(publisher.getId());
            model.addAttribute("publisher", newPublisher);
            return "EditPublisher.html";
        } catch (MyException ex){
            model.put("error", ex.getMessage());
            Publisher newPublisher = publisherService.getPublisher(publisher.getId());
            model.addAttribute("publisher", newPublisher);
            return "EditPublisher.html";            
        }
    }
}
