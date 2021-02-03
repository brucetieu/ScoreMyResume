package com.bruce.jobmatchr.webscrape;


import java.util.Objects;

public class Job {

    private String jobTitle;
    private String jobCompany;
    private String jobLocation;
    private String jobDescription;
    private String jobLink;
    private double jobMatchScore;

    public Job(String jobTitle, String jobCompany, String jobLocation, String jobDescription, String jobLink, double jobMatchScore) {
        this.jobTitle = jobTitle;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.jobDescription = jobDescription;
        this.jobLink = jobLink;
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

    public double getJobMatchScore() {
        return jobMatchScore;
    }

    public void setJobMatchScore(double jobMatchScore) {
        this.jobMatchScore = jobMatchScore;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Double.compare(job.jobMatchScore, jobMatchScore) == 0 && Objects.equals(jobTitle, job.jobTitle) && Objects.equals(jobCompany, job.jobCompany) && Objects.equals(jobLocation, job.jobLocation) && Objects.equals(jobDescription, job.jobDescription) && Objects.equals(jobLink, job.jobLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, jobCompany, jobLocation, jobDescription, jobLink, jobMatchScore);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobCompany='" + jobCompany + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobLink='" + jobLink + '\'' +
                ", jobMatchScore=" + jobMatchScore +
                '}';
    }
}
