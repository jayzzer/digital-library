package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAllByOrderByName();
}
