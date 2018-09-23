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
import org.hibernate.criterion.Restrictions;
import se.du.instagramdatacollector.dto.City;
import se.du.instagramdatacollector.dto.Country;
import se.du.instagramdatacollector.dto.Instagram;
import se.du.instagramdatacollector.dto.Location;

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
        if (className.equals("City")) {
            List<City> list = session.createCriteria(City.class).list();
            session.close();
            return list;

        }

        if (className.equals("Location")) {
            List<Location> list = session.createCriteria(Location.class).add(Restrictions.between("id", 1435730, 1436729)).list();
            session.close();
            return list;

        }
        return new ArrayList<>();

    }

    public static boolean isAvalableInstagram(String image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Instagram instagram = (Instagram) session.createCriteria(Instagram.class).add(Restrictions.eq("image", image)).uniqueResult();
        return instagram != null && instagram.getId() >= 1;
    }

    public static List getInstagram(Location location) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Location> list = session.createCriteria(Instagram.class).add(Restrictions.eq("location", location)).list();
        return list;
    }
}
