package com.bruce.jobmatchr.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GlassdoorScrape {

    public final static String URL = "https://www.indeed.com/jobs?q=";
    public static void scrape(String jobTitle, String jobLocation) {

        String queryURL = URL + jobTitle + "&l=" + jobLocation;
        System.out.println(queryURL);

        try {
            Document document = Jsoup.connect(queryURL).get();

            Elements jobCard = document.select("div.jobsearch-SerpJobCard");

            for (Element e : jobCard) {
                String href = e.select("a.jobtitle").attr("href");
                String pageAd = "https://www.indeed.com" + href;
                Document doc = Jsoup.connect(pageAd).get();
                System.out.println(doc.select("h1.jobsearch-JobInfoHeader-title").text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        GlassdoorScrape gs = new GlassdoorScrape();

        gs.scrape("software engineer", "denver");
    }
}
