package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Gif;

import java.util.List;

public interface GifDao {
    List<Gif> findAll();   //  Method that returns all the gifs
    Gif findById(Long id); //  Method that returns a gif specified by a certain id
    void save(Gif gif);   //  Method that saves a gif using the gif
    void delete(Gif gif); //  Method that deletes a gif
}// End class
