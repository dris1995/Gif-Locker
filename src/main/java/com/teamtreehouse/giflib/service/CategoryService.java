package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();   //  Method that returns all the categories
    Category findById(Long id); //  Method that returns a category specified by a certain id
    void save(Category category);   //  Method that saves a category
    void delete(Category category); //  Method that deletes a category
}// End class
