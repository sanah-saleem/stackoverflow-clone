package com.project.application.controller;


import com.project.application.domain.Author;
import com.project.application.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "registration-form";
    }

    @PostMapping("/registration")
    public String registerUserAccount(Model theModel,
                                      @ModelAttribute("author") Author author,
                                      HttpServletRequest request,
                                      Errors errors) {

        Author registered = authorService.findByEmail(author.getEmail());

        if (registered != null) {
//            theModel.addAttribute("user", registered);
            theModel.addAttribute("Error", "Email Already Exists");
            return "registration-form";
        }
        authorService.saveAuthor(author);
        return "login-form";
    }


}