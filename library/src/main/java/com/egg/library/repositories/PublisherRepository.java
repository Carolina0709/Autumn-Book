/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.egg.library.repositories;

import com.egg.library.entities.Publisher;
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
public interface PublisherRepository extends JpaRepository<Publisher, String> {
       
    Page<Publisher> findAllByOrderBySalesDesc(Pageable pageable);
    
    @Query("SELECT p FROM Publisher p ORDER BY p.sales DESC")
    public List<Publisher> findBestPublishers(Pageable pageable);
    
    @Override
    List<Publisher> findAll();
    
    @Query("SELECT p FROM Publisher p WHERE p.user.id = :userId ORDER BY p.sales DESC")
    Page<Publisher> findAllPublishersFromUser(@Param("userId") String userId, Pageable pageable);
}
