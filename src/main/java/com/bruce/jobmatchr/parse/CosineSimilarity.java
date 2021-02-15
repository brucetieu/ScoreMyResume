package com.bruce.jobmatchr.parse;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class CosineSimilarity {

    private List<String> wordsFromResumeFile;
    private List<String> wordsFromJobDescription;
    private Set<String> bagOfWords = new HashSet<>();
    Hashtable<String, Double> tfidfResume;
    Hashtable<String, Double> tfidfJobDesc;

    public CosineSimilarity(String jobDescriptionText, File resumeFile) throws IOException {
        TFIDF tfidf = new TFIDF();

        wordsFromResumeFile = TextExtraction.extractPDFText(resumeFile);
        wordsFromJobDescription = TextExtraction.extractText(jobDescriptionText);
        bagOfWords.addAll(wordsFromResumeFile);
        bagOfWords.addAll(wordsFromJobDescription);

        Hashtable<String, Double> tfResume = tfidf.computeTF(wordsFromResumeFile, bagOfWords);
        Hashtable<String, Double> tfJobDesc = tfidf.computeTF(wordsFromJobDescription, bagOfWords);

        // Create tf-idf vectors of each document.
        tfidfResume = tfidf.computeTFIDF(tfResume, wordsFromResumeFile, wordsFromJobDescription, bagOfWords);
        tfidfJobDesc = tfidf.computeTFIDF(tfJobDesc, wordsFromResumeFile, wordsFromJobDescription, bagOfWords);
    }


    public double cosineSimilarity() throws IOException {

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Then compute the cosine similarity between the documents.
        for (String word : tfidfResume.keySet()) {
            dotProduct += tfidfResume.get(word) * tfidfJobDesc.get(word);
            normA += Math.pow(tfidfResume.get(word), 2);
            normB += Math.pow(tfidfJobDesc.get(word), 2);
        }

        return normA != 0.0 || normB != 0.0 ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }
}