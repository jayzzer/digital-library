package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.Category;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/books/{id}")
    public String book(Model model, @PathVariable String id) {
        Optional<Book> bookOpt = bookRepository.findById(Integer.parseInt(id));
        Book book = bookOpt.get();

        model.addAttribute("view", "book");
        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        return "layout";
    }

    @GetMapping("/books")
    public String books(
            @RequestParam(name = "genre", required = false, defaultValue = "all") String genre,
            @RequestParam(name = "sort", required = false, defaultValue = "rating-d") String sort,
            @RequestParam(name = "p", required = false, defaultValue = "0") int pageIndex,
            Model model) {
        String[] sortParts = sort.split("-");

        Pageable perPage;
        switch (sortParts[1]) {
            case "d":
                perPage = PageRequest.of(pageIndex, 12, Sort.by(sortParts[0]).descending());

                break;
            case "u":
                perPage = PageRequest.of(pageIndex, 12, Sort.by(sortParts[0]).ascending());

                break;
            default:
                perPage = null;
                break;
        }

        Page<Book> bookPage;
        if (genre.equals("all")) {
            bookPage = bookRepository.findAll(perPage);
        } else {
            bookPage = bookRepository.findAllByGenre(genre, perPage);
        }

        List<Category> categories = categoryService.findAll();

        model.addAttribute("view", "books");
        model.addAttribute("title", "Каталог книг");

        model.addAttribute("books", bookPage);
        model.addAttribute("categories", categories);

        return "layout";
    }
}
