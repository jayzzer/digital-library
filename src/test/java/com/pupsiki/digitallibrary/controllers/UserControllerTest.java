package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.models.UserBooksDto;
import com.pupsiki.digitallibrary.models.UserDto;
import com.pupsiki.digitallibrary.models.VerificationToken;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealRepository dealRepository;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    //fails cause "No ModeAndView found"
    @Test
    public void user() throws Exception {
        User user = new User();
        user.setId(123);
        List<UserBooksDto> userBooks = Mockito.anyList();
        Mockito.when(dealRepository.getAllUserBooks(user.getId())).thenReturn(userBooks);
        this.mockMvc.perform(get("/user")).
                andDo(print()).
                andExpect(status().isFound()).
                andExpect(model().attribute("view", "userProfile")).
                andExpect(model().attribute("title", "Профиль пользователя")).
                andExpect(model().attribute("books", userBooks)).
                andExpect(redirectedUrl("/ulogin"));

    }

    @Test
    public void registrationPage() throws Exception{
       // UserDto user = new UserDto();
        //Mockito.when()
        this.mockMvc.perform(get("/registration")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "registration")).
                andExpect(model().attribute("title", "Регистрация")).
                andExpect(model().attributeExists("user"));
    }

    @Test
    public void loginPage() throws Exception{
        this.mockMvc.perform(get("/ulogin")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("view", "login")).
                andExpect(model().attribute("title", "Вход"));
    }

    @Test
    public void registration() {


    }

    @Test
    public void confirmRegistration() throws Exception {
        VerificationToken verificationToken = Mockito.mock(VerificationToken.class);
        //verificationToken.setToken("");
        //Model model = Mockito.mock(Model.class);
        //this.userController.confirmRegistration(model(),verificationToken.toString() );
        //int hash = verificationToken.hashCode();
        User user = new User();

        Mockito.when(userService.getVerificationToken("123")).thenReturn(verificationToken);
        this.mockMvc.perform(get("/registrationConfirm").
                            param("token", "123")).
                andDo(print()).
                //andExpect(model().attributeExists("message")).
               // andExpect().
                andExpect(redirectedUrl("/ulogin"));

    }
}