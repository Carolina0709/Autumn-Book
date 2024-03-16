/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.controllers;

import com.egg.library.entities.Author;
import com.egg.library.entities.Book;
import com.egg.library.entities.Publisher;
import com.egg.library.entities.User;
import com.egg.library.repositories.BookRepository;
import com.egg.library.services.AuthorService;
import com.egg.library.services.BookService;
import com.egg.library.services.PublisherService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author caro9
 */

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private PublisherService publisherService;
    
    @Autowired
    private UserService userService;
    

    @GetMapping("/see")
    public String seeBooks(@PageableDefault(size = 6) Pageable pageable, Model model) {
        Page<Book> books = bookRepository.findAllByOrderBySalesDesc(pageable);
        
        if(books.isEmpty()){
            model.addAttribute("empty", "No books to show");   
        }else{
            model.addAttribute("books", books);    
        }
       
        return "seeBooks.html";
    }

    @GetMapping("/see/{id}")
    public String seeBook(@PathVariable String id, Model model, Principal principal){
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("author", authorService.getAuthor(bookService.getBook(id).getAuthor().getId()));
        model.addAttribute("publisher", publisherService.getPublisher(bookService.getBook(id).getPublisher().getId()));

        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        return "seeBook.html";
    }

    @GetMapping("/create")
    public String createBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("authorsList", authorService.getAllAuthors());
        model.addAttribute("publishersList", publisherService.getAllPublishers());
        return "createBook.html";
    }
    
    @PostMapping("/create")
    public String createBook(@ModelAttribute("book") Book book, @RequestParam("file") MultipartFile file, @RequestParam("authorId") String authorId, @RequestParam("publisherId") String publisherId, Principal principal, ModelMap model) throws MyException {
        try {
            
            String userEmail = principal.getName();
            User user = userService.findByEmail(userEmail);
            
            if (authorId == null || publisherId == null || authorId.isEmpty() || publisherId.isEmpty()) {    
                model.put("error", "Neither Author nor Publisher can be empty");
                model.addAttribute("authorsList", authorService.getAllAuthors());
                model.addAttribute("publishersList", publisherService.getAllPublishers());
                return "createBook.html";
            }
            
            Author author = authorService.getAuthor(authorId);
            Publisher publisher = publisherService.getPublisher(publisherId);


            bookService.createBook(book.getName(), book.getForeword(), book.getPrice(), author, publisher, file, user);
            model.put("success", "Book created successfully.");
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "createBook.html";
        }
        return "createBook.html";
    }

    
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable String id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("authorsList", authorService.getAllAuthors());
        model.addAttribute("publishersList", publisherService.getAllPublishers());
        return "EditBook.html";
    }
    
    @PostMapping("/edit/{id}")
    public String editBook(@ModelAttribute("book") Book book, @RequestParam MultipartFile file, @RequestParam("authorId") String authorId, @RequestParam("publisherId") String publisherId, ModelMap model ) throws MyException{
        try {
            
            if (authorId == null || publisherId == null || authorId.isEmpty() || publisherId.isEmpty()) {    
                 model.put("error", "Neither Author nor Publisher can be empty");
                return "EditBook.html";
            }
            
            Author author = authorService.getAuthor(authorId);
            Publisher publisher = publisherService.getPublisher(publisherId);

            if (!file.isEmpty()) {
                bookService.editBook(book.getId(), book.getName(), book.getForeword(), book.getPrice(), author, publisher, file);
            } else {
                Book existingBook = bookService.getBook(book.getId());
                book.setImage(existingBook.getImage());
                bookService.editBookWithoutImage(book.getId(), book.getName(), book.getForeword(), book.getPrice(), author, publisher);
            }
            model.put("success", "Book edited successfully");
            Book newBook = bookService.getBook(book.getId());
            model.addAttribute("book", newBook);
            model.addAttribute("authorsList", authorService.getAllAuthors());
        model.addAttribute("publishersList", publisherService.getAllPublishers());
            return "EditBook.html";
        } catch (MyException ex){
            model.put("error", ex.getMessage());
            Book newBook = bookService.getBook(book.getId());
            model.addAttribute("book", newBook);
            model.addAttribute("authorsList", authorService.getAllAuthors());
        model.addAttribute("publishersList", publisherService.getAllPublishers());
            return "EditBook.html";            
        }
    }

    @GetMapping("/buy/{id}")
    public String buyBook(@PathVariable String id, Model model, Principal principal){
        
        String userEmail = principal.getName();
        User user = userService.findByEmail(userEmail);
        
        model.addAttribute("user", user);
        
       model.addAttribute("book", bookService.getBook(id));
       return "buyBook.html";
    }
 
    @PostMapping("/buy/{id}")
    public String buyBook(@PathVariable("id") String bookId, RedirectAttributes redirectAttributes) throws MyException {

        try {

            Book book = bookService.getBook(bookId);

            String authorId = book.getAuthor().getId();
            String publisherId = book.getPublisher().getId();

            bookService.buyBook(bookId);
            authorService.buyBook(authorId);
            publisherService.buyBook(publisherId);
            redirectAttributes.addFlashAttribute("success", "Successful purchase");
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("error", "Purchase not completed");
        }

        return "redirect:/books/buy/" + bookId;
    }
}
