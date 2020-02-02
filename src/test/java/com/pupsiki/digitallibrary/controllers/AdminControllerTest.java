package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.management.relation.Role;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc MockMvc;

    @Autowired
    private AdminController adminController;

    @Test
    public void adminTest() throws Exception{
        this.MockMvc.perform(get("/admin")).
                andDo(print());
      //          //andExpect(status().isOk()). user = 302; admin = 200
       //         andReturn(content().string(containsString("<h1>Hello, admin!</h1>")));
        //Assert.assertEquals("<h1>Hello, admin!</h1>", adminController.admin());
    }
}