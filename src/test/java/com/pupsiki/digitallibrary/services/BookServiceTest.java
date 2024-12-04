package com.pupsiki.digitallibrary.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void findBooks() {
        Integer countBooks = Math.toIntExact(bookService.findBooks("man", 1, 1).getTotalElements());
        assertThat(countBooks>0).isTrue();
    }
}