/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.library.repositories;

import com.egg.library.entities.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caro9
 */

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findAllByOrderBySalesDesc(Pageable pageable);
    
    @Query("SELECT b FROM Book b ORDER BY b.sales DESC")
    public List<Book> findBestBooks(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId ORDER BY b.sales DESC")
    public List<Book> findBestBooksFromAuthor(@Param("authorId") String authorId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.publisher.id = :publisherId ORDER BY b.sales DESC")
    public List<Book> findBestBooksFromPublisher(@Param("publisherId") String publisherId, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.user.id = :userId ORDER BY b.sales DESC")
    Page<Book> findAllBooksFromUser(@Param("userId") String userId, Pageable pageable);
}

