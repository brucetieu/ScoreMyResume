package com.bruce.jobmatchr.parse;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class TFIDF {

    Hashtable<String, Double> idfHash = new Hashtable<String, Double>();
    Hashtable<String, Double> tfidfHash = new Hashtable<String, Double>();

    public TFIDF() {
    }


    public Hashtable<String, Double> getFrequencyByWord(List<String> cleanedList, Set<String> unionOfWords) {
        Hashtable<String, Double> freqUniqueWords = new Hashtable<String, Double>();

        // Set the frequency of all words to be 0.
        for (String unionVal : unionOfWords) {
            freqUniqueWords.put(unionVal, 0.0);
        }

        // Count how many times the word appears in the cleanedList, populate those
        // counts as values in the hash table.
        for (String word : cleanedList) {
            freqUniqueWords.put(word, freqUniqueWords.get(word) + 1);
        }

        return freqUniqueWords;
    }

    public Hashtable<String, Double> computeTF(List<String> listOfWords, Set<String> unionOfWords) {
        Hashtable<String, Double> tfHash = new Hashtable<String, Double>();

        int termsInDoc = listOfWords.size();

        // Compute the term frequency of each word.
        // TF(t) = (Number of times term t appears in a document) / (Total number of
        // terms in the document).
        for (String word : getFrequencyByWord(listOfWords, unionOfWords).keySet()) {
            tfHash.put(word, ((double) getFrequencyByWord(listOfWords, unionOfWords).get(word) / (double) termsInDoc));

        }

        return tfHash;
    }


    public Hashtable<String, Double> computeTFIDF(Hashtable<String, Double> tf, List<String> wordsFromResume, List<String> wordsFromJobDescription, Set<String> unionOfWords) {

        computeIDF(wordsFromResume, wordsFromJobDescription, unionOfWords);

        // tfidf(t, d, D) = tf(t,d) + tf(t,d) * idf(t, D).
        for (String word : tf.keySet()) {
            tfidfHash.put(word, tf.get(word) + (tf.get(word) * idfHash.get(word)));
        }

        return tfidfHash;
    }

    private void computeIDF(List<String> wordsFromResume, List<String> wordsFromJobDescription, Set<String> unionOfWords) {

        // Create a list of hash tables.
        List<Hashtable<String, Double>> listOfHashes = new ArrayList<Hashtable<String, Double>>();

        // Add the frequency tables of words of each document to the list.
        listOfHashes.add(getFrequencyByWord(wordsFromResume, unionOfWords));
        listOfHashes.add(getFrequencyByWord(wordsFromJobDescription, unionOfWords));

        int numOfDocuments = 2;

        // Set all values in the idf hash table to be 0.
        for (String word : listOfHashes.get(0).keySet()) {
            idfHash.put(word, 0.0);
        }

        // Count the number of documents with a term t (word) in it.
        for (Hashtable<String, Double> hashtable : listOfHashes) {
            for (String word : hashtable.keySet()) {
                if (hashtable.get(word) > 0) {
                    idfHash.put(word, (double) idfHash.get(word) + 1);
                }
            }
        }

        // IDF(t) = ln(Total number of documents / Number of documents with term t in
        // it).
        for (String word : idfHash.keySet()) {
            double numDocWithTermT = idfHash.get(word);
            double idf = Math.log((double) numOfDocuments / numDocWithTermT);

            // Replace divide by 0 errors with a 0.
            if (numDocWithTermT == 0)  idfHash.put(word, 0.0);
            else idfHash.put(word, idf);
        }

    }
}
