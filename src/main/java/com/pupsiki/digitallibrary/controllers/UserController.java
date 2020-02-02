package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.annotations.EmailExistsException;
import com.pupsiki.digitallibrary.models.*;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.services.OnRegistrationCompleteEvent;
import com.pupsiki.digitallibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

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
        } else {
            try {
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                        (registered, Locale.ENGLISH, appUrl));
            } catch (Exception me) {
                System.out.println(me);
                ModelAndView modelAndView = new ModelAndView("layout", "user", user);
                modelAndView.addObject("view", "registration");
                modelAndView.addObject("title", "Регистрация");

                return modelAndView;
            }

            return new ModelAndView("redirect:/user");
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = service.getVerificationToken(token);

        if (verificationToken == null) {
            String message = "Неправильный токен!";
            model.addAttribute("message", message);
            return "redirect:/ulogin";
        }

        User user = verificationToken.getUser();

        user.setActive(true);
        service.saveRegisteredUser(user);
        return "redirect:/ulogin";
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
