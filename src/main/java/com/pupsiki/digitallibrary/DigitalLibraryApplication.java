package com.pupsiki.digitallibrary;

import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalLibraryApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DigitalLibraryApplication.class, args);
    }

//    @Bean
//    public void createUser() {
//        userRepository.save(new User("lal@gmail.com", "pass", true, "ROLE_USER"));
//    }
}
