package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.Category;
import com.pupsiki.digitallibrary.repositories.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAll() {
        List<Category> categories = categoryService.findAll();
        assertThat(categories).isNotNull();
        assertThat(categories.size() > 0).isTrue();

    }
}