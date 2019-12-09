package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Book> topFreeBooks = bookRepository.findTop10ByPriceOrderByRatingDesc(0f);
        List<Book> topNew = bookRepository.findTop10ByOrderByCreatedAtDesc();
        List<Book> topBooks = bookRepository.findTop10ByOrderByRatingDesc();

        model.addAttribute("view", "index");
        model.addAttribute("title", "Главная страница");
        model.addAttribute("topFreeBooks", topFreeBooks);
        model.addAttribute("topNew", topNew);
        model.addAttribute("topBooks", topBooks);

        return "layout";
    }
}
