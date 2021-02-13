package com.bruce.jobmatchr.parse;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class CosineSimilarity {



    /**
     * This calculates the cosine similarity of two documents.
     *
     * @param docA The TextDocument object storing the text of the first document.
     * @param docA The TextDocument object storing the text of the second document.
     * @return The cosine similarity of two documents.
     * @throws IOException Catch any file errors.
     */
    private static Set<String> bagOfWords = new HashSet<>();

    public static double cosineSimilarity(String jobDescriptionText, String resumeFile) throws IOException {

        TFIDF tfidf = new TFIDF();

        List<String> wordsFromResumeFile = TextExtraction.extractPDFText(new File(resumeFile));
        List<String> wordsFromJobDescription = TextExtraction.extractText(jobDescriptionText);
        bagOfWords.addAll(wordsFromResumeFile);
        bagOfWords.addAll(wordsFromJobDescription);

        Hashtable<String, Double> tfResume = tfidf.computeTF(wordsFromResumeFile, bagOfWords);
        Hashtable<String, Double> tfJobDesc = tfidf.computeTF(wordsFromJobDescription, bagOfWords);

        // Create tf-idf vectors of each document.
        Hashtable<String, Double> tfidfResume = tfidf.computeTFIDF(tfResume);
        Hashtable<String, Double> tfidfJobDesc = tfidf.computeTFIDF(tfJobDesc);


        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // Then compute the cosine similarity between the documents.
        for (String word : tfidfResume.keySet()) {
            dotProduct += tfidfResume.get(word) * tfidfJobDesc.get(word);
            normA += Math.pow(tfidfResume.get(word), 2);
            normB += Math.pow(tfidfJobDesc.get(word), 2);
        }
        return normA != 0 || normB != 0 ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }
}