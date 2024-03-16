/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.Publisher;
import com.egg.library.repositories.AuthorRepository;
import com.egg.library.repositories.BookRepository;
import com.egg.library.repositories.PublisherRepository;
import com.egg.library.services.UserService;
import exceptions.MyException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Controller
@RequestMapping("/")
public class controllerPortal {
    
    @Autowired
    UserService userService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 4) Pageable pageable) {

        List<Book> bestBooks = bookRepository.findBestBooks(pageable);

        if (bestBooks.isEmpty()) {
            model.addAttribute("emptyBooks", "No Books to show");
        } else {
            model.addAttribute("books", bestBooks);
        }

        Author bestAuthor = authorRepository.findFirstByOrderBySalesDesc();

        if (bestAuthor == null) {
            model.addAttribute("emptyAuthor", "No Author to show");
        } else {
            model.addAttribute("author", bestAuthor);
        }

        Pageable publishersPageable = PageRequest.of(0, 3);
        List<Publisher> bestPublishers = publisherRepository.findBestPublishers(publishersPageable);

        if (bestPublishers.isEmpty()) {
            model.addAttribute("emptyPublishers", "No Publishers to show");
        } else {
            model.addAttribute("publishers", bestPublishers);
        }

        return "index.html";
    }
       
    @GetMapping("signin")
    public String signin(){
        return "signin.html";
    }
    
    @GetMapping("signinSuccessed")
    public String signinSuccessed(){
        return "signinSuccessed.html";
    }
    
    @PostMapping("signin")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam String address, @RequestParam("file") MultipartFile file, ModelMap model) throws MyException{
        try{
            userService.createUser(name, email, password, password2, address, file);
             
        }catch(MyException ex){
            model.put("error", ex.getMessage());
            return "signin.html";
        }
        return "redirect:/signinSuccessed";
    }
       
    @GetMapping("login")
    public String login(@RequestParam(required = false) String error, ModelMap model){
        if(error != null){
            model.put("error", "Incorrect email or password");
        }
        return "login.html";
    }
    
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied.html";
    }
    
    @GetMapping("/notFound")
    public String notFound() {
        return "notFound.html"; 
    }
}
