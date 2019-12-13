package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.Category;
import com.pupsiki.digitallibrary.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"categories"})
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Cacheable
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByName();
    }
}
