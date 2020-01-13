package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsername_true() {
        UserDetails userDetails=customUserDetailsService.loadUserByUsername("admin@booklya.ru");
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("admin@booklya.ru");

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_false() {
        UserDetails userDetails=customUserDetailsService.loadUserByUsername("nousernameindb@mail");
    }
}
