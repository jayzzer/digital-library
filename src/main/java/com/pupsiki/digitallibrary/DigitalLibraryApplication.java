package com.pupsiki.digitallibrary;

import com.github.javafaker.Faker;
import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.User;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@SpringBootApplication
public class DigitalLibraryApplication {

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(DigitalLibraryApplication.class, args);
    }

//    @Bean
//    public void seedBooks() {
//        Faker faker = new Faker(new Locale("ru"));
//        for (int i = 0; i < 20; i++) {
//            bookRepository.save(new Book(
//                    faker.book().title(),
//                    "book" + getRandomNumberInRange(1, 7),
//                    faker.book().author(),
//                    faker.book().publisher(),
//                    faker.book().genre(),
//                    faker.lorem().paragraph(10),
//                    faker.number().numberBetween(1900, 2019),
//                    0,
//                    (float) faker.number().randomDouble(1, 0, 5)
//            ));
//        }
//    }

//    @Bean
//    public void showByRating() {
//        List<Book> topBooks = bookRepository.findTop10ByPriceOrderByRatingDesc(0f);
//        for (Book book : topBooks) {
//            System.out.println(book.getTitle() + " " + book.getRating() + " " + book.getPrice());
//        }
//    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
