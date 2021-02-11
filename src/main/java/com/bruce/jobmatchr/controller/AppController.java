package com.bruce.jobmatchr.controller;


import com.bruce.jobmatchr.document.UserDocument;
import com.bruce.jobmatchr.document.UserDocumentRepository;
import com.bruce.jobmatchr.parse.TextDocument;
import com.bruce.jobmatchr.user.User;
import com.bruce.jobmatchr.user.UserRepository;
import com.bruce.jobmatchr.webscrape.IndeedDataService;
import com.bruce.jobmatchr.webscrape.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class AppController {

    @Autowired
    private UserDocumentRepository userDocRepo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndeedDataService indeedDataService;

    @GetMapping("/")
    public String viewHomepage() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user, RedirectAttributes redirAttrs) throws SQLIntegrityConstraintViolationException{

        // Save the registered user in the db
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // If a duplicate entry exists in db, flash a message.
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/register?error";
        }


        return "registration_success";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/get_started")
    public String viewGatheringInfo(Model model) {
        return "get_started";
    }


    @PostMapping("/get_started")
    public String viewMatchingScreen(@RequestParam("customFile") MultipartFile multipartFile, @RequestParam("jobTitle") String jobTitle,
                                     @RequestParam("jobLocation") String jobLocation, @RequestParam("jobDescriptionText") String jobDescriptionText,
                                     Model model, RedirectAttributes ra, Principal principal) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UserDocument userDocument = new UserDocument();
        userDocument.setResumeFile(fileName);
        userDocument.setContent(multipartFile.getBytes());
        userDocument.setSize(multipartFile.getSize());
        userDocument.setUploadTime(new Date());

        TextDocument jobText = new TextDocument(jobDescriptionText);
        TextDocument resumeText = new TextDocument(new File(fileName));

        double cosineSimilarity = CosineSimilarity.cosineSimilarity(jobText, resumeText);

        User currentUser = userRepository.findByEmail(principal.getName());
        currentUser.setUserDocument(userDocument);
        userDocument.setUser(currentUser);

        userRepository.save(currentUser);


        model.addAttribute("cosineSimilarity", cosineSimilarity);

        ra.addAttribute("message", "Generating your results!");

        return "get_started";
    }

    @GetMapping("/matching")
    public String viewMatches(Model model) {

        indeedDataService.scrape("software engineer", "remote");
        model.addAttribute("jobs", indeedDataService.getJobPosting());

        return "matching";
    }
}
