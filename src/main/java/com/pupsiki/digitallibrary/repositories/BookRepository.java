package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
    List<Book> findAll();
    List<Book> findTop10ByOrderByRatingDesc();
    List<Book> findTop10ByOrderByCreatedAtDesc();
    List<Book> findTop10ByPriceOrderByRatingDesc(float price);

    Page<Book> findAllByGenre(String genre, Pageable pageable);
    Page<Book> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
}
