package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> findAll() {

        /* Hibernate 5.2.0 *********************************************************************
     // Open a session
    Session session = sessionFactory.openSession();

    // DEPRECATED as of Hibernate 5.2.0
    // List<Category> categories = session.createCriteria(Category.class).list();

    // UPDATED: Create CriteriaBuilder
    CriteriaBuilder builder = session.getCriteriaBuilder();

    // UPDATED: Create CriteriaQuery
    CriteriaQuery<Category> criteria = builder.createQuery(Category.class);

    // UPDATED: Specify criteria root
    criteria.from(Category.class);

    // UPDATED: Execute query
    List<Category> categories = session.createQuery(criteria).getResultList();

    // Close session
    session.close();

    return categories;

         *********************************************************************************************************/

        //  Open session
        Session session = sessionFactory.openSession();

        //  Get all categories with a Hibernate criteria
        List<Category> categories = session.createCriteria(Category.class).list();

        //  Close the session
        session.close();
        return categories;
    }

    @Override
    public Category findById(Long id) {
        Session session = sessionFactory.openSession();
        Category category = session.get(Category.class,id);
        Hibernate.initialize(category.getGifs());
        session.close();
        return category;    }

    @Override
    public void save(Category category) {

        //  Open a session
        Session session = sessionFactory.openSession();

        //  Begin a transaction
        session.beginTransaction();

        //  Save the category
        session.saveOrUpdate(category);

        //  Commit the transaction
        session.getTransaction().commit();

        //  Close the session
        session.close();
    }// End save

    @Override
    public void delete(Category category) {
        //  Open a session
        Session session = sessionFactory.openSession();

        //  Begin a transaction
        session.beginTransaction();

        //  Save the category
        session.delete(category);

        //  Commit the transaction
        session.getTransaction().commit();

        //  Close the session
        session.close();
    }




}// End class
