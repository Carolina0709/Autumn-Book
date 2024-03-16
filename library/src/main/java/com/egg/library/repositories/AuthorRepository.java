/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.library.repositories;

import com.egg.library.entities.Author;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caro9
 */

@Repository
public interface AuthorRepository extends JpaRepository<Author, String>{
    
    Page<Author> findAllByOrderBySalesDesc(Pageable pageable);
    
    Author findFirstByOrderBySalesDesc();
    
    @Override
    List<Author> findAll();
    
    @Query("SELECT a FROM Author a WHERE a.user.id = :userId ORDER BY a.sales DESC")
    Page<Author> findAllAuthorsFromUser(@Param("userId") String userId, Pageable pageable);
}
