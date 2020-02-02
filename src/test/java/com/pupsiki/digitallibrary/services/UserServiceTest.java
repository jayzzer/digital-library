package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.annotations.EmailExistsException;
import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.models.UserDto;
import com.pupsiki.digitallibrary.models.VerificationToken;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import com.pupsiki.digitallibrary.repositories.VerificationTokenRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
//@Sql(value = {})
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    //private UserService userService = Mockito.mock(UserService.class);

    @MockBean
    private UserRepository userRepository;
    //private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @MockBean
    private VerificationTokenRepository tokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerNewUserAccount() throws EmailExistsException {

        //User user1 = userRepository.findByEmail("test@gmail.com");
        //User user2 = userRepository.findByEmail("test1@gmail.com");

        //Assert.assertEquals();
        //userService.registerNewUserAccount(userRepository.findByEmail("test@mail.ru"));
        //UserDto userDto = new UserDto();
        //User user = new User();
        //this.mockMvc.perform(post()).andExpect(status().isCreated());

        //User user = Mockito.mock(User.class);

        //userDto.setEmail("test2@gmail.com");
        //userDto.setPassword("1234");
        //userDto.setRepeatPassword("1234");
        //userDto.setName("Dru");
        //userDto.setSurname("Jones");

        //Mockito.when(userRepository.save(user)).thenReturn(user);
        //Assert.assertEquals(userDto, user);
        //Assert.assertEquals(userDto.getName(), user.getName());
        //Assert.assertTrue(user.getRoles().matches("ROLE_USER"));
        //Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    public void saveRegisteredUser() {
        User user = new User();
        userService.saveRegisteredUser(user);
        //Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void createVerificationToken() {
        User user = new User();
        String token = "";
        VerificationToken myToken = new VerificationToken(token, user);
        userService.createVerificationToken(user, token);
        Mockito.verify(tokenRepository, Mockito.times(1)).save(Mockito.refEq(myToken));
    }

    @Test
    public void registerNewUserAccountTest() {
        UserDto userDto  = Mockito.mock(UserDto.class);
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("1234");
        userDto.setName("user");
        userDto.setSurname("1");

        User user = Mockito.mock(User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRoles("ROLE_USER");

        //account does not exist
        Mockito.when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);// true (null)/false
        //Mockito.verify(userRepository,Mockito.times(1)).save(user);
        //Mockito.when(userRepository.save(user)).thenReturn()
        try {
            userService.registerNewUserAccount(userDto);
            Assert.fail();

        } catch (EmailExistsException e) {
            Assert.assertEquals(e.getMessage(),"There is an account with that email address: " + user.getEmail());
        }
        //Assert.assertEquals(userService.registerNewUserAccount(userDto),userRepository.save(user));

        //Mockito.when(dtoMock.getEmail()).thenReturn("1");
        //Mockito.when(dtoMock.getPassword()).thenReturn("pass");


    }

}