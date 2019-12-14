package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.Deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends CrudRepository<Deal, Integer> {

    @Query(value = "select book_id from Deal deal where deal.user_id = :userId", nativeQuery = true)
    List<Integer> getUserBooks(@Param("userId") int userId);

}