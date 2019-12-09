package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findTop10ByOrderByRatingDesc();
    List<Book> findTop10ByOrderByCreatedAtDesc();
    List<Book> findTop10ByPriceOrderByRatingDesc(float price);
}
