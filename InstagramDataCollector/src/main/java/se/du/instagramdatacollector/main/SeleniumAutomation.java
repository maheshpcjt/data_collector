/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import se.du.instagramdatacollector.HibernateQuery;
import se.du.instagramdatacollector.HibernateUtil;
import se.du.instagramdatacollector.dto.City;
import se.du.instagramdatacollector.dto.Comment;
import se.du.instagramdatacollector.dto.Country;
import se.du.instagramdatacollector.dto.Instagram;
import se.du.instagramdatacollector.dto.Location;

/**
 *
 * @author mcha
 */
public class SeleniumAutomation {

    private static final String LOCATION_URL = "https://www.instagram.com/explore/locations/";

    private static final String BASE_URL = "https://www.instagram.com";

    private static final String COUNTRY_CLASS = "vLP4P";

    private static final String COUNTRY_LI = "kiTXG";

    private static final WebDriver webDriver = new ChromeDriver();

    public static void main(String[] args) {
        openDriver();

//        getCountry();
//        getCity();
//        getLocation();
//        getImageDetails();
//        getMoreImageDetails();
        fixMissingDetails();
        closeDriver();
    }

    public static void openDriver() {
        webDriver.navigate().to(LOCATION_URL);
    }

    public static void getCountry() {
        try {
            try {
                while (webDriver.findElement(By.className(COUNTRY_CLASS)).isEnabled()) {
                    TimeUnit.SECONDS.sleep(5);
                    webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                }
            } catch (NoSuchElementException e) {

            }
            TimeUnit.SECONDS.sleep(5);
            String pageSource = webDriver.getPageSource();
            Document instagramLocationPage = Jsoup.parse(pageSource);
            Elements locationCountries = instagramLocationPage.getElementsByClass(COUNTRY_LI);
            List<Object> countries = new ArrayList<>();

            for (Element country : locationCountries) {
                String name = country.getElementsByTag("a").text();
                String url = country.getElementsByTag("a").first().attr("href");
                countries.add(new Country(name, url));
            }

            HibernateQuery.add(countries);
        } catch (InterruptedException ex) {
            Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void closeDriver() {
        webDriver.close();
        webDriver.quit();
    }

    public static void getCity() {
        List<Country> countries = HibernateQuery.get("Country");

        for (Country country : countries) {
            try {
                webDriver.get(BASE_URL + country.getUrl());
                try {
                    while (webDriver.findElement(By.className(COUNTRY_CLASS)).isEnabled()) {
                        TimeUnit.SECONDS.sleep(2);
                        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                        webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                    }
                } catch (NoSuchElementException e) {

                }
                TimeUnit.SECONDS.sleep(2);
                String pageSource = webDriver.getPageSource();
                Document instagramLocationPage = Jsoup.parse(pageSource);
                Elements locationCities = instagramLocationPage.getElementsByClass(COUNTRY_LI);
                List<Object> cities = new ArrayList<>();
                for (Element city : locationCities) {
                    String name = city.getElementsByTag("a").text();
                    System.out.println(name);
                    String url = city.getElementsByTag("a").first().attr("href");
                    System.out.println(url);

                    cities.add(new City(country, name, url));
                }

                //  HibernateQuery.add(cities);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    public static void getLocation() {
        List<City> cities = HibernateQuery.get("City");

        for (City city : cities) {
            if (city.getId() > 0) {
                try {
                    webDriver.get(BASE_URL + city.getUrl());
                    try {
                        while (webDriver.findElement(By.className(COUNTRY_CLASS)).isEnabled()) {
                            TimeUnit.SECONDS.sleep(2);
                            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                            webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                        }
                    } catch (NoSuchElementException e) {

                    }
                    TimeUnit.SECONDS.sleep(2);
                    String pageSource = webDriver.getPageSource();
                    Document instagramLocationPage = Jsoup.parse(pageSource);
                    Elements locationPlaces = instagramLocationPage.getElementsByClass(COUNTRY_LI);
                    List<Object> locations = new ArrayList<>();
                    for (Element location : locationPlaces) {
                        String name = location.getElementsByTag("a").text();
                        System.out.println(name);
                        String url = location.getElementsByTag("a").first().attr("href");
                        System.out.println(url);

                        locations.add(new Location(city, name, url));
                    }

                    // HibernateQuery.add(locations);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }

    }

    public static void getImageDetails() {
        List<Location> locations = HibernateQuery.get("Location");

        for (Location location : locations) {
            if (location.getCity().getId() >= 2026 && location.getCity().getId() <= 2520) {
                try {
                    webDriver.get(BASE_URL + location.getUrl());
                    try {
                        while (webDriver.findElement(By.className(COUNTRY_CLASS)).isEnabled()) {
                            TimeUnit.SECONDS.sleep(2);
                            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                            webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                        }
                    } catch (NoSuchElementException e) {

                    }

                    TimeUnit.SECONDS.sleep(2);
                    try {
                        if (webDriver.findElement(By.className("eLAPa")).isDisplayed()) {
                            webDriver.findElement(By.className("eLAPa")).click();

                            for (int i = 0; i < 200; i++) {
                                // List<Object> instagrams = new ArrayList<>();
                                TimeUnit.SECONDS.sleep(2);
                                webDriver.findElement(By.className("coreSpriteRightPaginationArrow")).click();
                                TimeUnit.SECONDS.sleep(2);
                                String pageSource = webDriver.getPageSource();
                                Document instagramImagePage = Jsoup.parse(pageSource);
                                Elements instagramPosts = instagramImagePage.getElementsByClass("nJAzx");

                                String imageDetails = instagramImagePage.getElementsByClass("FFVAD").first().attr("src");
                                String uploaderName = instagramPosts.first().getElementsByTag("a").text();
                                try {
                                    String uploaderURL = instagramPosts.first().getElementsByTag("a").first().attr("href");
                                    String likeCount = instagramImagePage.getElementsByClass("zV_Nj").first().text().replace("likes", "").replace(",", "").trim();
                                    String uploadedDateTime = instagramImagePage.getElementsByClass("c-Yi7").first().getElementsByTag("time").first().attr("datetime");
                                    System.out.println(" " + uploaderName + " " + uploaderURL + " " + imageDetails + " " + likeCount + " " + uploadedDateTime);
                                    Elements comments = instagramImagePage.getElementsByClass("C4VMK");
                                    // List<Object> commentList = new ArrayList<>();

                                    Session session = HibernateUtil.getSessionFactory().openSession();
                                    session.beginTransaction();

                                    Set<Comment> commentsList = new HashSet<Comment>();
                                    Instagram instagram = new Instagram(imageDetails, Integer.parseInt(likeCount), uploaderName, uploaderURL, location, uploadedDateTime);

                                    session.save(instagram);
                                    for (Element comment : comments) {
                                        String text = comment.getElementsByTag("span").text();
                                        System.out.println("comment " + text);
                                        Comment comment1 = new Comment(text, instagram);
                                        commentsList.add(comment1);
                                        session.save(comment1);
                                    }

                                    instagram.setComment(commentsList);

                                    session.getTransaction().commit();
                                } catch (NullPointerException e) {
                                }

                            }
                        }
                    } catch (Exception e) {
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
    }

    public static void getMoreImageDetails() {
        List<Location> locations = HibernateQuery.get("Location");

        for (Location location : locations) {
            if (location.getCity().getId() >= 2026 && location.getCity().getId() <= 2520) {
                try {
                    webDriver.get(BASE_URL + location.getUrl());
                    try {
                        while (webDriver.findElement(By.className(COUNTRY_CLASS)).isEnabled()) {
                            TimeUnit.SECONDS.sleep(2);
                            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                            webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                        }
                    } catch (NoSuchElementException e) {

                    }

                    TimeUnit.SECONDS.sleep(2);
                    try {
                        if (webDriver.findElement(By.className("eLAPa")).isDisplayed()) {
                            webDriver.findElement(By.className("eLAPa")).click();

                            for (int i = 0; i < 200; i++) {
                                // List<Object> instagrams = new ArrayList<>();
                                TimeUnit.SECONDS.sleep(2);
                                webDriver.findElement(By.className("coreSpriteRightPaginationArrow")).click();
                                TimeUnit.SECONDS.sleep(2);
                                String pageSource = webDriver.getPageSource();
                                Document instagramImagePage = Jsoup.parse(pageSource);
                                Elements instagramPosts = instagramImagePage.getElementsByClass("nJAzx");

                                String imageDetails = instagramImagePage.getElementsByClass("FFVAD").first().attr("src");
                                if (imageDetails != null && !HibernateQuery.isAvalableInstagram(imageDetails)) {
                                    String uploaderName = instagramPosts.first().getElementsByTag("a").text();
                                    try {
                                        String uploaderURL = instagramPosts.first().getElementsByTag("a").first().attr("href");

                                        String likeCount = instagramImagePage.getElementsByClass("zV_Nj").first().text().replace("likes", "").replace(",", "").trim();
                                        String uploadedDateTime = instagramImagePage.getElementsByClass("c-Yi7").first().getElementsByTag("time").first().attr("datetime");
                                        System.out.println(" " + uploaderName + " " + uploaderURL + " " + imageDetails + " " + likeCount + " " + uploadedDateTime);
                                        Elements comments = instagramImagePage.getElementsByClass("C4VMK");
                                        // List<Object> commentList = new ArrayList<>();

                                        Session session = HibernateUtil.getSessionFactory().openSession();
                                        session.beginTransaction();

                                        Set<Comment> commentsList = new HashSet<>();
                                        Instagram instagram = new Instagram(imageDetails, Integer.parseInt(likeCount), uploaderName, uploaderURL, location, uploadedDateTime);

                                        session.save(instagram);
                                        for (Element comment : comments) {
                                            String text = comment.getElementsByTag("span").text();
                                            System.out.println("comment " + text);
                                            Comment comment1 = new Comment(text, instagram);
                                            commentsList.add(comment1);
                                            session.save(comment1);
                                        }

                                        instagram.setComment(commentsList);

                                        session.getTransaction().commit();
                                    } catch (NullPointerException e) {
                                        System.out.println(e);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
            }
        }
    }

    public static void fixMissingDetails() {
        List<Location> locations = HibernateQuery.get("Location");

        for (Location location : locations) {
            List instagramList = HibernateQuery.getInstagram(location);
            if (instagramList.size() <= 200) {
                try {
                    webDriver.get(BASE_URL + location.getUrl());
                    try {
                       // for (int i = 0; i < 100; i++) {
                            TimeUnit.SECONDS.sleep(2);
                            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                           // webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                       // }
                    } catch (NoSuchElementException e) {

                    }

                    TimeUnit.SECONDS.sleep(2);
                    try {
                        //for (int i = 0; i < 100; i++) {
                             ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0,-document.body.scrollHeight)");
                              TimeUnit.SECONDS.sleep(2);
                      //  }
                        TimeUnit.SECONDS.sleep(2);
                        if (webDriver.findElement(By.className("eLAPa")).isDisplayed()) {
                            webDriver.findElement(By.className("eLAPa")).click();
                            int index = 1;
                            while (HibernateQuery.getInstagram(location).size() <= 200) {
                                // List<Object> instagrams = new ArrayList<>();
                                TimeUnit.SECONDS.sleep(2);
                                webDriver.findElement(By.className("coreSpriteRightPaginationArrow")).click();
                                TimeUnit.SECONDS.sleep(2);
                                String pageSource = webDriver.getPageSource();
                                Document instagramImagePage = Jsoup.parse(pageSource);
                                Elements instagramPosts = instagramImagePage.getElementsByClass("nJAzx");
                                Elements instagramImages = instagramImagePage.getElementsByClass("FFVAD");
                                //KL4Bh
                                String imageDetails = instagramImages.get(index).attr("src");
                                if (imageDetails != null && !HibernateQuery.isAvalableInstagram(imageDetails)) {
                                    String uploaderName = instagramPosts.first().getElementsByTag("a").text();
                                    try {
                                        String uploaderURL = instagramPosts.first().getElementsByTag("a").first().attr("href");

                                        String likeCount = instagramImagePage.getElementsByClass("zV_Nj").first().text().replace("likes", "").replace(",", "").trim();
                                        if (likeCount.contains("like")){
                                        likeCount=likeCount.replace("like", "").trim();
                                        }
                                        
                                        String uploadedDateTime = instagramImagePage.getElementsByClass("c-Yi7").first().getElementsByTag("time").first().attr("datetime");
                                        System.out.println(" " + uploaderName + " " + uploaderURL + " " + imageDetails + " " + likeCount + " " + uploadedDateTime);
                                        Elements comments = instagramImagePage.getElementsByClass("C4VMK");
                                        // List<Object> commentList = new ArrayList<>();

                                        Session session = HibernateUtil.getSessionFactory().openSession();
                                        session.beginTransaction();

                                        Set<Comment> commentsList = new HashSet<>();
                                        Instagram instagram = new Instagram(imageDetails, Integer.parseInt(likeCount), uploaderName, uploaderURL, location, uploadedDateTime);

                                        session.save(instagram);
                                        for (Element comment : comments) {
                                            String text = comment.getElementsByTag("span").text();
                                            System.out.println("comment " + text);
                                            Comment comment1 = new Comment(text, instagram);
                                            commentsList.add(comment1);
                                            session.save(comment1);
                                        }

                                        instagram.setComment(commentsList);

                                        session.getTransaction().commit();
                                    } catch (NullPointerException e) {
                                        System.out.println(e);
                                    }
                                }
                                index ++;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
            }
        }
    }
}
