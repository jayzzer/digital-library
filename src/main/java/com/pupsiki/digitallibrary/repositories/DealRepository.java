package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.Deal;
import com.pupsiki.digitallibrary.models.UserBooksDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends CrudRepository<Deal, Integer> {

    @Query(value = "select book_id from Deal deal where deal.user_id = :userId", nativeQuery = true)
    List<Integer> getUserBooks(@Param("userId") int userId);

    @Query(value = "select book.id as id, title, image, author, publisher, genre, description, year, price, rating from Deal deal, Book book where deal.user_id = :userId AND book.id = deal.book_id", nativeQuery = true)
    List<UserBooksDto> getAllUserBooks(@Param("userId") int userId);
}