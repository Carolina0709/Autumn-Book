/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.library.services;

import com.egg.library.entities.Image;
import com.egg.library.repositories.ImageRepository;
import exceptions.MyException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author caro9
 */

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    
    public Image SaveImage(MultipartFile file) throws MyException{
        if(file != null){
            try{
                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                
                return imageRepository.save(image);
                
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    public Image updateImage(MultipartFile file, String idImage) throws MyException{
        if(file != null){
            try{
                Image image = new Image();
                
                if(idImage != null){
                    Optional<Image> answer = imageRepository.findById(idImage);
                    
                    if(answer.isPresent()){
                        image = answer.get();
                    }
                }
                
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                
                return imageRepository.save(image);
                
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
            
}
