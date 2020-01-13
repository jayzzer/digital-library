package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.annotations.EmailExistsException;
import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.models.UserDto;
import com.pupsiki.digitallibrary.models.VerificationToken;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import com.pupsiki.digitallibrary.repositories.VerificationTokenRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VerificationTokenRepository tokenRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerNewUserAccount() throws EmailExistsException {
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

        Mockito.when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);

        try {
            userService.registerNewUserAccount(userDto);
            Assert.assertEquals(userService.registerNewUserAccount(userDto),userRepository.save(user));
        } catch (EmailExistsException e) {
            Assert.assertEquals(e.getMessage(),"There is an account with that email address: " + user.getEmail());
        }
    }

    @Test
    public void registerExistedUserAccount() {
        UserDto userDto  = Mockito.mock(UserDto.class);
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("1234");
        userDto.setName("user");
        userDto.setSurname("1");

        User user = Mockito.mock(User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(null);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRoles("ROLE_USER");

        Mockito.when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);

        try {
            userService.registerNewUserAccount(userDto);
        } catch (EmailExistsException e) {
            Assert.assertEquals(e.getMessage(),"There is an account with that email address: " + userDto.getEmail());
        }
    }

    @Test
    public void getUser_false() {
    }

    @Test
    public void getVerificationToken() {
        String token = "string";
        Assert.assertEquals(tokenRepository.findByToken(token),userService.getVerificationToken(token));
    }

    @Test
    public void getVerificationToken_false() {
        VerificationToken verificationToken=userService.getVerificationToken("blablabla");
        assertThat(verificationToken).isNull();
    }

    @Test
    public void saveRegisteredUser() {
        User user = Mockito.mock(User.class);
        userService.saveRegisteredUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void createVerificationToken() {
        User user = new User();
        String token = "";
        VerificationToken myToken = new VerificationToken(token, user);
        userService.createVerificationToken(user, token);
        Mockito.verify(tokenRepository, Mockito.times(1)).save(Mockito.refEq(myToken));
    }


}