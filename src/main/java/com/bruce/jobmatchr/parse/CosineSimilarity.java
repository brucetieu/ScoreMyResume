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

    public CosineSimilarity(String jobDescriptionText, String resumeFile) throws IOException {
        TFIDF tfidf = new TFIDF();

        wordsFromResumeFile = TextExtraction.extractPDFText(new File(resumeFile));
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

    public static void main (String[] args) throws IOException {
        String job = "\n" +
                "Like to shake things up? We do. eBay hires innovators who can push boundaries and change the world. Design, develop and implement core software systems or applications. Think globally and help eBay be on the top tier with new products, including cloud-based or internet-related tools.\n" +
                "\n" +
                "We are looking for a motivated software engineer to build robust and scalable software, help improve our code services and system architecture, participate in brainstorming sessions and supply ideas to our technology, algorithms and products, and work with the product and design teams to understand end-user requirements, formulate use cases, and deliver results. We want the highest levels of technical talent and programming skills, as well as a keen desire to deeply understand our products and services to push our technology forward. You’ll work alongside the best and the brightest engineering talent in the industry. As a core participant of your team, you’ll estimate engineering efforts, prioritize projects, plan implementations, and triage production issues. You need to be dynamic, collaborative, and curious as we build new experiences, improve existing products, and develop distributed systems powering the world’s largest e-commerce websites at a scale few companies can match.\n" +
                "\n" +
                "Note: By applying to this position, your application will be considered for locations we hire for in the United States including, but not limited to: San Jose, CA; Bellevue, WA; Portland, OR; and New York, NY.\n" +
                "\n" +
                "You are:\n" +
                "• Analytical. Able to convert abstract concepts into viable products using CS fundamentals\n" +
                "• Resourceful. Have the ability to do a lot with a little. Be able to aggregate information from various places and build relationships with key partners to get the information you need\n" +
                "• Data Driven. Able to use data to frame out and solve problems\n" +
                "\n" +
                "We need:\n" +
                "• Programming expertise. Be proficient at object oriented programming concepts, design patters, and SDLCA technical connoisseur. Be comfortable with algorithms and data structures, such as dynamic array, linked list, stack, queue, binary search, binary search tree, hash map, depth first search, breadth first search, and merge sort.\n" +
                "• A go-getter. Seek new opportunities, don’t shy away from challenges, and bring energy and enthusiasm to the office every day.\n" +
                "\n" +
                "Basic Qualifications:\n" +
                "• Must be currently enrolled in a full-time, degree-seeking program and in the process of obtaining a Bachelors or Masters degree in Computer Science or a related field\n" +
                "• Strong applied experience. You’ve built, broken, and rebuilt software applications\n" +
                "• Creative thinker who knows how to create real-world products\n" +
                "• Key skills: Java, Python, distributed systems, cloud-related knowledge";

        String file = "/Users/bruce/Documents/Job-Hunt-2020-2021/BT_SWE_Resume_Edit2_2020.pdf";

        CosineSimilarity cs = new CosineSimilarity(job, file);
        System.out.println(cs.cosineSimilarity());
    }
}