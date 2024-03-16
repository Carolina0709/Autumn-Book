
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.services;

import com.egg.library.entities.Image;
import com.egg.library.entities.User;
import com.egg.library.enumerations.Role;
import com.egg.library.repositories.UserRepository;
import exceptions.MyException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void createUser(String name, String email, String password, String password2,  String address, MultipartFile file) throws MyException {
        
        validateUser(name, email, password, password2, address, file);
        
        User user = new User();
        
        user.setName(name);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAddress(address);
        user.setRole(Role.USER);
        
        Image image = imageService.SaveImage(file);
        user.setImage(image);
        
        userRepository.save(user);
    
    }
    
    
    public void validateUser(String name, String email, String password, String password2, String address, MultipartFile file) throws MyException{
        if(name == null || name.isEmpty()){
            throw new MyException("Name can not be empty");
        }
        if(email == null || email.isEmpty()){
            throw new MyException("Email can not be empty");
        }
        
        if(password == null || password.isEmpty() || password.length() <= 5){
            throw new MyException("Password can not be empty or be less than 5 characters");
        }
        
        if(!password.equals(password2)){
            throw new MyException("Passwords must match");
        }
        
        if(address == null || address.isEmpty()){
            throw new MyException("Address can not be empty");
        }
        
        if(file == null || file.isEmpty()){
            throw new MyException("Image can not be empty");
        }  
        
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null && email.equals(existingUser.getEmail())) {
            throw new MyException("Email already exists, please log in.");
        }
       
        
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Transactional()
    public User getUser(String id) {
        return userRepository.getOne(id);
    }

    
    @Transactional
    public void editUserEmail(String idUser, String email) throws MyException {
       
        if(email == null || email.isEmpty()){
            throw new MyException("Email can not be empty");
        }
        
        Optional<User> answer = userRepository.findById(idUser);
   
        if (answer.isPresent()) {
            User user = answer.get();

            user.setEmail(email);

            userRepository.save(user);
        }
       
    
    }
        
    @Transactional
    public void editUserRole(String idUser, String role) throws MyException {
       
        if(role == null || role.isEmpty()){
            throw new MyException("Role can not be empty");
        }
        
        Optional<User> answer = userRepository.findById(idUser);
  
        if (answer.isPresent()) {
            User user = answer.get();

            Role enumRole = Role.valueOf(role.toUpperCase());

            // Asignar el nuevo rol al usuario
            user.setRole(enumRole);

            userRepository.save(user);
        }

    }
    
    @Transactional
    public void editUserImage(String idUser, MultipartFile file) throws MyException {
       
        if(file == null || file.isEmpty()){
            throw new MyException("Image can not be empty");
        } 
        
        Optional<User> answer = userRepository.findById(idUser);
   
        if (answer.isPresent()) {
            User user = answer.get();

            String idImage = null;
            
            if(user.getImage() != null){
                idImage = user.getImage().getId();
            }
            
            Image image = imageService.updateImage(file, idImage);
            user.setImage(image);

            userRepository.save(user);
        }
       
    
    }
    
    @Transactional
    public void editUserPassword(String idUser, String password) throws MyException {
       
        if(password == null || password.isEmpty()){
            throw new MyException("Password can not be empty");
        }
        
        Optional<User> answer = userRepository.findById(idUser);
   
        if (answer.isPresent()) {
            User user = answer.get();

            user.setPassword(new BCryptPasswordEncoder().encode(password));

            userRepository.save(user);
        }
       
    
    }

    @Transactional
    public void editUserAddress(String idUser, String address) throws MyException {
       
        if(address == null || address.isEmpty()){
            throw new MyException("Address can not be empty");
        }
        
        Optional<User> answer = userRepository.findById(idUser);
   
        if (answer.isPresent()) {
            User user = answer.get();

            user.setAddress(address);

            userRepository.save(user);
        }
       
    
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())
            );
            
           ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
           
           HttpSession session = attr.getRequest().getSession(true);
           
           session.setAttribute("loggedUser", user);

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
    }

}
