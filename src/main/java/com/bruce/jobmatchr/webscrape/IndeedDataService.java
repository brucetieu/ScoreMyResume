package com.bruce.jobmatchr.webscrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class IndeedDataService {

    private final String INDEED = "https://www.indeed.com";
    private final String QUERY_URL = INDEED + "/jobs?q=";
    private final String jobDiv = "div.jobsearch-SerpJobCard";
    private Set<Job> jobPosting = new HashSet<>();


    public Set<Job> getJobPosting() {
        return jobPosting;
    }

    public void scrape(String jobTitle, String jobLocation) {

        String fullQueryURL = QUERY_URL + jobTitle + "&l=" + jobLocation;
        System.out.println(fullQueryURL);

        try {
            Document document = Jsoup.connect(fullQueryURL).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:85.0) Gecko/20100101 Firefox/85.0").get();

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

                    System.out.println(pageAd);

                    Document doc = Jsoup.connect(pageAd).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:85.0) Gecko/20100101 Firefox/85.0").get();

                    Element element1 = doc.getElementById("jobDescriptionText");
//                    Element element2 = doc.getElementById("iCIMS_InfoMsg iCIMS_InfoMsg_Job");

                    String jobDescription = "none";
                    if (element1 != null) {
                        jobDescription = doc.getElementById("jobDescriptionText").text();
                    }
                    jobPosting.add(new Job(title, company, location, jobDescription, pageAd, 0.0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return jobPosting;
    }

//    public static void main(String[] args) {
//        GlassdoorScrape gs = new GlassdoorScrape();
//
//        gs.scrape("software engineer", "remote");
//    }


}
