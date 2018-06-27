/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import se.du.instagramdatacollector.dto.Country;

/**
 *
 * @author
 */
public class HibernateQuery {

    public static void add(List<Object> list) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer dealID = null;
        for (Object deal : list) {
            try {
                tx = session.beginTransaction();
                dealID = (Integer) session.save(deal);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            }
        }
        session.close();
    }

    public static List get(String className) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (className.equals("Country")) {
            List<Country> list = session.createCriteria(Country.class).list();
            session.close();
            return list;
            
        }
        return new ArrayList<>();

    }
}
