package com.bruce.jobmatchr.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;

public class GlassdoorScrape {

    private final String INDEED = "https://www.indeed.com";
    private final String QUERY_URL = INDEED + "/jobs?q=";
    private final String jobDiv = "div.jobsearch-SerpJobCard";
    private Set<Job> jobPosting;


    public Set<Job> scrape(String jobTitle, String jobLocation) {

        String fullQueryURL = QUERY_URL + jobTitle + "&l=" + jobLocation;
        System.out.println(fullQueryURL);

        try {
            Document document = Jsoup.connect(fullQueryURL).get();

            Elements pagination = document.select("ul.pagination-list > li > a");

            for (Element p : pagination) {
                String url = p.attr("href");
                System.out.println(url);

                Document page = Jsoup.connect(INDEED + url).get();

                Elements jobCard = page.select(jobDiv);

                for (Element e : jobCard) {
                    String title = e.select("h2.title").text();
                    String company = e.select("span.company").text();
                    String location = e.select("div.location").text();

                    String href = e.select("a.jobtitle").attr("href");
                    String pageAd = INDEED + href;

                    Document doc = Jsoup.connect(pageAd).get();

                    String jobDescription = doc.getElementById("jobDescriptionText").text();

                    jobPosting.add(new Job(title, company, location, jobDescription, pageAd, 0.0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jobPosting;
    }


    public static void main (String[] args) {
        GlassdoorScrape gs = new GlassdoorScrape();

        gs.scrape("software engineer", "denver");
    }
}
