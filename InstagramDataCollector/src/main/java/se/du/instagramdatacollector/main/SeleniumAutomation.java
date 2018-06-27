/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
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
import se.du.instagramdatacollector.dto.City;
import se.du.instagramdatacollector.dto.Country;

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
        getCountry();
        getCity();
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
                        ((JavascriptExecutor)webDriver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
                        webDriver.findElement(By.className(COUNTRY_CLASS)).click();
                    }
                } catch (NoSuchElementException e) {

                }
                TimeUnit.SECONDS.sleep(5);
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

                HibernateQuery.add(cities);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeleniumAutomation.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }
}
