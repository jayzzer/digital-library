package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.Book;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public Page<Book> findBooks(String text, int page, int perPage) {
        Query query = getQueryBuilder()
                .keyword()
                .fuzzy()
                .onFields("title", "author", "genre")
                .matching(text)
                .createQuery();

        FullTextQuery fullTextQuery = getJpaQuery(query);
        int totalElements = fullTextQuery.getResultSize();
        fullTextQuery.setFirstResult(page * perPage);
        fullTextQuery.setMaxResults(perPage);

        Page<Book> books = new PageImpl<>(fullTextQuery.getResultList(), PageRequest.of(page, perPage), totalElements);
        return books;
    }

    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

        return fullTextQuery;
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Book.class)
                .get();
    }
}

