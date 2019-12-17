package com.pupsiki.digitallibrary;

import com.github.javafaker.Faker;
import com.pupsiki.digitallibrary.models.*;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.CategoryRepository;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@EnableCaching
public class DigitalLibraryApplication {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(DigitalLibraryApplication.class, args);
    }

//    @Bean
//    public void seedBooks() {
//        Faker faker = new Faker(new Locale("ru"));
//        for (int i = 0; i < 10; i++) {
//            bookRepository.save(new Book(
//                    faker.book().title(),
//                    "book" + getRandomNumberInRange(1, 7),
//                    faker.book().author(),
//                    faker.book().publisher(),
//                    faker.book().genre(),
//                    faker.lorem().paragraph(10),
//                    faker.number().numberBetween(1900, 2019),
//                    faker.number().numberBetween(0, 0),
//                    (float) faker.number().randomDouble(1, 0, 5)
//            ));
//        }
//    }

//    @Bean
//    public void testElastic() {
//        List<Book> books = bookRepository.findByTitle("No");
//        System.out.println(books.toString());
//    }

//    @Bean
//    public void seedCategories() {
//        Faker faker = new Faker(new Locale("ru"));
//        Set<String> uniqueCategories = new HashSet<>();
//        while (uniqueCategories.size() < 10) {
//            String category = faker.book().genre();
//            uniqueCategories.add(category);
//        }
//
//        for (String uniqueCategory : uniqueCategories) {
//            categoryRepository.save(new Category(
//                    uniqueCategory
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
