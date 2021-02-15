package com.bruce.jobmatchr.controller;


import com.bruce.jobmatchr.parse.CosineSimilarity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AppController {


    @GetMapping("/")
    public String viewHomepage() {
        return "index";
    }


    @PostMapping("/match_results")
    public String viewMatchingScreen(@RequestParam("customFile") MultipartFile multipartFile,
                                     @RequestParam("jobDescriptionText") String jobDescriptionText, Model model,
                                     RedirectAttributes ra) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        // Set a file path so that multipart can get converted to a file
        Path filepath = Paths.get("/Users/bruce/Documents/JobMatchr", multipartFile.getOriginalFilename());

        // Write the contents of multipart file into the file
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        }


        CosineSimilarity cosSim = new CosineSimilarity(jobDescriptionText, filepath.toFile());
        double cosSimVal = cosSim.cosineSimilarity();

        ra.addFlashAttribute("message", "Generated job match score!");
        ra.addFlashAttribute("alertClass", "alert-success");

        model.addAttribute("cosineSimilarity", cosSimVal);
        return "match_results";

    }

}
