package com.bruce.jobmatchr.parse;


import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class TFIDF {
    private List<String> wordsFromResume;
    private List<String> wordsFromJobDescription;
    private Set<String> unionOfWords;


    public TFIDF(File resumeFile, String jobDescriptionText) throws IOException {
        this.wordsFromResume = TextExtraction.extractPDFText(resumeFile);
        this.wordsFromJobDescription = TextExtraction.extractText(jobDescriptionText);
        unionOfWords.addAll(this.wordsFromResume);
        unionOfWords.addAll(this.wordsFromJobDescription);
    }

    public Hashtable<String, Double> getFrequencyByWord() {

    }

    public Hashtable<String, Double> computeTF(List<String> listOfWords) {

    }

    public Hashtable<String, Double> computeIDF() {

    }

    public Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf) {

    }
}
