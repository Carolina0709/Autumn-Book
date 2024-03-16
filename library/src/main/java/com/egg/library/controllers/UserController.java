/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.repositories.AuthorRepository;
import com.egg.library.repositories.BookRepository;
import com.egg.library.repositories.PublisherRepository;
import com.egg.library.services.UserService;
import exceptions.MyException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/dashboard")
    public String dashboard(@PageableDefault(size=6) Pageable pageable, Model model, Principal principal){
        
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        
        Page<Book> books = bookRepository.findAllBooksFromUser(user.getId(), pageable);
        
        if(books.isEmpty()){
            model.addAttribute("emptyBooks", "No books to show");   
        }else{
            model.addAttribute("books", books);    
        }
       
        
        Page<Author> authors = authorRepository.findAllAuthorsFromUser(user.getId(),pageable);
        
        if(authors.isEmpty()){
            model.addAttribute("emptyAuthors", "No authors to show");   
        }else{
            model.addAttribute("authors", authors);    
        }
        
        Page<Publisher> publishers = publisherRepository.findAllPublishersFromUser(user.getId(), pageable);
        
        if(publishers.isEmpty()){
            model.addAttribute("emptyPublishers", "No publishers to show");   
        }else{
            model.addAttribute("publishers", publishers);    
        }
        
       
        
        return "dashboard.html";
    }
    
    
    @PostMapping("/edit/email/{id}")
    public String editUserEmail(@ModelAttribute("user") User user, @RequestParam String email, ModelMap model ) throws MyException {
        try {
            
            userService.editUserEmail(user.getId(), email);

            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/login";
            
        } catch (MyException ex){
          
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";           
        }
    }
    
    @PostMapping("/edit/role/{id}")
    public String editUserRole(@ModelAttribute("user") User user, @RequestParam String role, ModelMap model ) throws MyException {
        try {
            
            userService.editUserRole(user.getId(), role);

            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/login";
            
        } catch (MyException ex){
         
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";          
        }
    }
    
    @PostMapping("/edit/image/{id}")
    public String editUserImage(@ModelAttribute("user") User user, @RequestParam MultipartFile file, ModelMap model ) throws MyException {
        try {
            
            if (!file.isEmpty()) {
                userService.editUserImage(user.getId(), file);
            }
            
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";
            
        } catch (MyException ex){
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";          
        }
    }
    
    @PostMapping("/edit/password/{id}")
    public String editUserPassword(@ModelAttribute("user") User user, @RequestParam String newPassword, ModelMap model ) throws MyException {
        try {
            
            userService.editUserPassword( user.getId(), newPassword);
 
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/login";
            
        } catch (MyException ex){
           
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";         
        }
    }
    
    @PostMapping("/edit/address/{id}")
    public String editUserAddress(@ModelAttribute("user") User user, @RequestParam String address, ModelMap model ) throws MyException {
        try {
            
            userService.editUserAddress(user.getId(), address);

            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard"; 
            
        } catch (MyException ex){
            User newUser = userService.getUser(user.getId());
            model.addAttribute("user", newUser);
            return "redirect:/user/dashboard";           
        }
    }
    
}
