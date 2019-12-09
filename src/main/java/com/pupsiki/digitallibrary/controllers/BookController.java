package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

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
    public String books(Model model) {
        model.addAttribute("view", "books");
        model.addAttribute("title", "Каталог книг");

        return "layout";
    }
}
