package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.models.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAll() {
        categoryService = Mockito.mock(CategoryService.class);
        ArrayList<Category> categoryList = new ArrayList <Category>();
        Mockito.when(categoryService.findAll()).thenReturn(categoryList);
        List<Category> categories =  categoryService.findAll();
        Assert.assertEquals(categories, categoryList);
    }
}