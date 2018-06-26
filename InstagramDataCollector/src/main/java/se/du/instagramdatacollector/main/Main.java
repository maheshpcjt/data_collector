/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author mcha
 */
public class Main {

//    private static String URL = "https://www.instagram.com/graphql/query/?query_hash=ded47faa9a1aaded10161a2ff32abb6b&variables=%7B%22tag_name%22%3A%22london%22%2C%22first%22%3A12%2C%22after%22%3A%22AQDMtFijYXM4_o5nnqLHJYxPCROX5mpCMVtXA_JIJ-pJuaAIosh1X3r4CoV0Qr4CI7gMfnY8WTC8R9XleFmheYGjl4HOO06JrA0RMAy-6xQg0Q%22%7D";
    private static String URL = "https://www.instagram.com";

    static SSLContext sc = null;

    static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36";
        
    public static void main(String[] args) {
        try {
//            getHTML(URL);
           logInClientEvents();
//getRequest();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getDetails(String URL) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    getHTML(URL);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
//        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
//        System.setProperty("http.proxyHost", "proxy.com");
//        System.setProperty("http.proxyPort", "911");

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        disableCertificateValidation(conn);
        conn.setRequestMethod("GET");
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        conn.addRequestProperty("authority", "www.instagram.com");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }

        Document doc = Jsoup.parse(result.toString());
        System.out.println("" + doc.html());
//        Elements divs = doc.select("div[class$=sold-property-listing]");
//        List<House> houseList = new ArrayList<>();
//
//        for (Element mainDiv : divs) {
//
//            Element div = mainDiv.select("div[class$=sold-property-listing__location]").first();
//            String type = div.getElementsByTag("title").text();
//            String location = div.select("div[class$=sold-property-listing__location]").text();
//            location = location.replaceAll(type, "");
//            System.out.println(location);
//            if (div == null) {
//                continue;
//            }
//
//            System.out.println("************************");
//            div = mainDiv.select("div[class$=sold-property-listing__size]").first();
//            String size = div.select("div[class$=sold-property-listing__subheading sold-property-listing--left]").text();
//            if (size.contains("m² ")) {
//                size = size.split("m² ")[1].trim().split("rum")[0];
//            } else {
//                size = size.split(" rum")[0].replace("rum", "");
//            }
//
//            String rent = div.select("div[class$=sold-property-listing__fee]").text();
//            if (div == null) {
//                continue;
//            }
//            //sold-property-listing__fee
//            div = mainDiv.select("div[class$=sold-property-listing__price]").first();
//            String price = div.select("div[class$=clear-children]").text();
//            String data = div.getAllElements().html();
//            System.out.println("Type " + type + " Size " + size + " Rent " + rent + " Price ");
//            String[] split = price.split("Såld");
//
//            String value = price.split("Slutpris ")[1].split(" kr Såld")[0].replaceAll("\u00C2", "\"");
//
//            int date = Integer.parseInt(split[1].split(" ")[1]);
//            String dateS = "";
//            if (date < 10) {
//                dateS = "0" + date;
//            } else {
//                dateS = "" + date;
//            }
//
//            String month = split[1].split(" ")[2];
//
//            if (month.equalsIgnoreCase("januari")) {
//                month = "-01-";
//            } else if (month.equalsIgnoreCase("februari")) {
//                month = "-02-";
//            } else if (month.equalsIgnoreCase("mars")) {
//                month = "-03-";
//            } else if (month.equalsIgnoreCase("april")) {
//                month = "-04-";
//            } else if (month.equalsIgnoreCase("maj")) {
//                month = "-05-";
//            } else if (month.equalsIgnoreCase("juni")) {
//                month = "-06-";
//            } else if (month.equalsIgnoreCase("juli")) {
//                month = "-07-";
//            } else if (month.equalsIgnoreCase("augusti")) {
//                month = "-08-";
//            } else if (month.equalsIgnoreCase("september")) {
//                month = "-09-";
//            } else if (month.equalsIgnoreCase("oktober")) {
//                month = "-10-";
//            } else if (month.equalsIgnoreCase("november")) {
//                month = "-11-";
//            } else if (month.equalsIgnoreCase("december")) {
//                month = "-12-";
//            }
//            dateS = split[1].split(" ")[3] + month + dateS;
//
//            House house = new House(type, location, size, price.split("Slutpris")[1].split("kr Såld")[0].replaceAll(" ", ""), price, rent, rent, dateS);
//            houseList.add(house);
//        }
//
//        HibernateQuery.add(houseList);
        return result.toString();
    }

    private static void disableCertificateValidation(HttpsURLConnection connection) {

        if (sc == null) {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }};
            try {
                // Install the all-trusting trust manager
                sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, null);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        });
    }

    private static void logInClientEvents() throws MalformedURLException, IOException {
        String url = "https://www.instagram.com/explore/locations/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "page=6";  // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    private static void getRequest() throws MalformedURLException, IOException {
        String url = "https://www.instagram.com/explore/locations/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
}
