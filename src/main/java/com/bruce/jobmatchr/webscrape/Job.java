package com.bruce.jobmatchr.webscrape;


public class Job {

    private String jobTitle;
    private String jobCompany;
    private String jobLocation;
    private String jobDescription;
    private String jobMatchScore;

    public Job(String jobTitle, String jobCompany, String jobLocation, String jobDescription, String jobMatchScore) {
        this.jobTitle = jobTitle;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.jobDescription = jobDescription;
        this.jobMatchScore = jobMatchScore;
    }

    public Job() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobMatchScore() {
        return jobMatchScore;
    }

    public void setJobMatchScore(String jobMatchScore) {
        this.jobMatchScore = jobMatchScore;
    }
}
