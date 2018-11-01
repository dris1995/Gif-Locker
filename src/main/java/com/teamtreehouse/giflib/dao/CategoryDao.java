package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();   //  Method that returns all the categories from the database
    Category findById(Long id); //  Method that returns a category specified by a certain id in the database
    void save(Category category);   //  Method that saves a category to the database
    void delete(Category category); //  Method that deletes a category from the database
}// End class
