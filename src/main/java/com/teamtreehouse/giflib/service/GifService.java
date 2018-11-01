package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.model.Gif;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GifService {
    List<Gif> findAll();   //  Method that returns all the gifs
    Gif findById(Long id); //  Method that returns a gif specified by a certain id
    void save(Gif gif, MultipartFile file);   //  Method that saves a gif using the gif and the file name
    void delete(Gif gif); //  Method that deletes a gif
    void toggleFavorite(Gif gif); //    Mthod that marks gif as favorite
    List<Gif> getFavoriteGifs(); //  Method that returns all the gifs marked as favoite
    List<Gif> searchByName(String search); //  Method that returns all the gifs by a search
}// End class
