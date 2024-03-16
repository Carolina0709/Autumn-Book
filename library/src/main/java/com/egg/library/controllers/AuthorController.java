/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.User;
import com.egg.library.repositories.AuthorRepository;
import com.egg.library.repositories.BookRepository;
import com.egg.library.services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/see")
    public String seeAuthors(@PageableDefault(size=6) Pageable pageable, Model model){
        Page<Author> authors = authorRepository.findAllByOrderBySalesDesc(pageable);
        
        if(authors.isEmpty()){
            model.addAttribute("empty", "No authors to show");   
        }else{
            model.addAttribute("authors", authors);    
        }
        
        return "seeAuthors.html";
    }
    
    @GetMapping("/see/{id}")
    public String seeAuthor(@PathVariable String id, Model model, @PageableDefault(size = 3) Pageable pageable, Principal principal) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);

        List<Book> bestBooks = bookRepository.findBestBooksFromAuthor(author.getId(), pageable);
        
        if(bestBooks.isEmpty()){
            model.addAttribute("empty", "No books to show");   
        }else{
            model.addAttribute("books", bestBooks);  
        }
        
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        
        return "seeAuthor";
    }

    
    @GetMapping("/create")
    public String createAuthor(Model model){
        model.addAttribute("author", new Author());
        return "createAuthor.html";
    }
    
    @PostMapping("/create")
    public String createAuthor(@ModelAttribute("author") Author author, @RequestParam("file") MultipartFile file, ModelMap model, Principal principal) throws MyException {
        try {
            
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);
            
            authorService.createAuthor(author.getName(), author.getMessage(), file, user);
            model.put("success", "Author created successfully.");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "createAuthor.html";
        }
        return "createAuthor.html";
    }

    
    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable String id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "editAuthor.html";
    }
    
    
    
    @PostMapping("/edit/{id}")
    public String editAuthor(@ModelAttribute("author") Author author, @RequestParam MultipartFile file, ModelMap model ){
        try {
            if (!file.isEmpty()) {
                authorService.editAuthor(author.getId(), author.getName(), author.getMessage(), file);
            } else {
                Author existingAuthor = authorService.getAuthor(author.getId());
                author.setImage(existingAuthor.getImage());
                authorService.editAuthorWithoutImage(author.getId(), author.getName(), author.getMessage());
            }
            model.put("success", "Author edited successfully");  
            Author newAuthor = authorService.getAuthor(author.getId());
            model.addAttribute("author", newAuthor);
            return "editAuthor.html";
        } catch (MyException ex){
            model.put("error", ex.getMessage());
            Author newAuthor = authorService.getAuthor(author.getId());
            model.addAttribute("author", newAuthor);
            return "editAuthor.html";            
        }
    }

}
