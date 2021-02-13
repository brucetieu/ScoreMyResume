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
        return normA != 0 || normB != 0 ? dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)) : 0.0;
    }

    public static void main (String[] args) throws IOException {
        String job = "Amazon devices impact the world with new technology every day. We are hiring a Software Development Engineer to help us grow. In this role, you will have the opportunity to work across one of the world’s fastest growing and most innovative product portfolios and product lines, including Amazon Echo, Fire TV, Fire Tablets, Kindle E-Readers, and more. The engineering team is revolutionizing the way customers buy those products. Our agile approach allows us to react quickly to market conditions and offer cutting-edge buying options. We operate in a fast-paced and highly experimental fashion, which allows us to bring the most value to our customer.\n" +
                "As a Software Development Engineer on our team, you will have the opportunity to design the next greatest idea to enhance the customers buying experience. SDEs at Amazon are expected to have industry-leading technical abilities which enable them to significantly improve product quality. We are looking for proactive, creative, flexible candidates who thrive within a collaborative and fun peer environment. This is a fast-paced team where you will make a direct impact on the customer experience and the bottom line of the company. We utilize OO programming languages and the cutting-edge AWS technology stack to build solutions that solve our challenges, ranging from scaling for massive Amazon order volume to supporting customers around the world.\n" +
                "If you’re a customer-centric, passionate developer who feels strongly about building world-class software, let's talk.\n" +
                "\n" +
                "\n" +
                "Basic Qualifications\n" +
                "\n" +
                "    Programming experience with at least one modern language such as Java, C++, or C# including object-oriented design\n" +
                "    2+ years of non-internship professional software development experience\n" +
                "    Programming experience with at least one modern language such as Java, C++, or C# including object-oriented design\n" +
                "    1+ years of experience contributing to the architecture and design (architecture, design patterns, reliability and scaling) of new and current systems\n" +
                "    Bachelor's degree in Computer Science or another technical field, or commensurate professional experience.\n" +
                "    Deep understanding of CS fundamentals including data structures, algorithms and complexity analysis\n" +
                "    Design and architecture knowledge as well as familiarity with object oriented analysis and design patterns (OOA/OOD)\n" +
                "\n" +
                "Preferred Qualifications\n" +
                "\n" +
                "    2+ years of professional software development experience\n" +
                "    Bachelor's degree in Computer Science or another technical field, or commensurate professional experience.\n" +
                "    Proficiency in at least one modern object-oriented programming language such as Java, C++ or C#\n" +
                "    Deep understanding of CS fundamentals including data structures, algorithms and complexity analysis\n" +
                "    Design and architecture knowledge as well as familiarity with object oriented analysis and design patterns (OOA/OOD)\n" +
                "    Experience developing cloud software services and an understanding of design for scalability, performance and reliability\n" +
                "    Development experience defining, developing and maintaining web service API's\n" +
                "    Experience in communicating with users, other technical teams, and senior management to collect requirements, describe software product features, technical designs, and product strategy\n" +
                "    Experience taking a core role in building complex software systems that have been successfully delivered to customers\n" +
                "    Knowledge of professional software engineering practices & best practices for the full software development life cycle, including coding standards, code reviews, source control management, build processes, testing, and operations\n" +
                "    Experience with distributed computing and enterprise-wide systems\n" +
                "    Ability to thrive in fast-paced, dynamic environment";

        String file = "/Users/bruce/Documents/Job-Hunt-2020-2021/BT_SWE_Resume_Edit2_2020.pdf";

        CosineSimilarity cs = new CosineSimilarity(job, file);
        System.out.println(cs.cosineSimilarity());
    }
}