/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.du.instagramdatacollector;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import se.du.instagramdatacollector.dto.Instagram;
import se.du.instagramdatacollector.dto.Comment;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author 
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().addAnnotatedClass(Instagram.class).addAnnotatedClass(Comment.class).buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
