/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.egg.library.repositories;

import com.egg.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caro9
 */

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);
    
}
