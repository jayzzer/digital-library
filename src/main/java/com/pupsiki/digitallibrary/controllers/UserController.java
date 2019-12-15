package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.annotations.EmailExistsException;
import com.pupsiki.digitallibrary.models.*;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private DealRepository dealRepository;

    @GetMapping("/user")
    public String user(Model model, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<UserBooksDto> userBooks = dealRepository.getAllUserBooks(user.getId());

        model.addAttribute("view", "userProfile");
        model.addAttribute("title", "Профиль пользователя");
        model.addAttribute("books", userBooks);

        return "layout";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("view", "registration");
        model.addAttribute("title", "Регистрация");
        UserDto user = new UserDto();
        model.addAttribute("user", user);

        return "layout";
    }

    @GetMapping("/ulogin")
    public String loginPage(
            Model model
    ) {
        model.addAttribute("view", "login");
        model.addAttribute("title", "Вход");

        return "layout";
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @ModelAttribute("user") @Valid UserDto user,
            BindingResult result, WebRequest request, Errors errors) {

        if (result.getGlobalErrors().size() > 0) {
            result.rejectValue("password", "error.password", "Пароли должны совпадать!");
        }

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(user, result);
        }
        if (registered == null) {
            result.rejectValue("email", "error.user", "Аккаунт с данным email уже существует!");
        }
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("layout", "user", user);
            modelAndView.addObject("view", "registration");
            modelAndView.addObject("title", "Регистрация");

            return modelAndView;
        }
        else {
            return new ModelAndView("redirect:/user");
        }
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        try {
            registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}
