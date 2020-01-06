package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.Book;
import org.hibernate.search.jpa.FullTextQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceTest {

//    @MockBean
//    private BookService bookService;

    @Test
    public void findBooks() {
        BookService bookService = new BookService();
        int page = 1;
        int perPage = 15;
        FullTextQuery fullTextQuery = Mockito.mock(FullTextQuery.class);
        int totalElements = fullTextQuery.getResultSize();
        fullTextQuery.setFirstResult(page * perPage);
        fullTextQuery.setMaxResults(perPage);
        String text = fullTextQuery.toString();


        List<Book> list = new ArrayList<>();
        //list.add(new Book("bookname", "book1.jpg", "author", "romashka", "genre", "dwacr", 1999, 235, 1234f));
        Page<Book> books = new PageImpl<Book>(list, PageRequest.of(page, 12), totalElements);

        assertEquals(books,
                bookService.findBooks(fullTextQuery.getResultList().toString(), page, perPage));
    }

//    @Test
//    public void getJpaQuery(){
//        BookService bookService = new BookService();
//        org.apache.lucene.search.Query luceneQuery =
//                new Mockito().mock(org.apache.lucene.search.Query.class);
//        EntityManager entityManager =
//                new Mockito().mock(EntityManager.class);
//        entityManager.createQuery("SELECT FROM ");
//        Mockito.when(ent)
//
//    }
}