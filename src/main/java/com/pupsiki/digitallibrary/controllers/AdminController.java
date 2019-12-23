package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.annotations.StorageException;
import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.BookDto;
import com.pupsiki.digitallibrary.models.Category;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.CategoryRepository;
import com.pupsiki.digitallibrary.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static float getRandomNumberInRange(float min, float max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (float) (min + Math.random() * (max - min));
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @RequestParam(name = "p", required = true, defaultValue = "0") int pageIndex
    ) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(pageIndex, 15,  Sort.by("createdAt").descending()));

        model.addAttribute("title", "Панель администратора");
        model.addAttribute("view", "adminPage");
        model.addAttribute("books", books);

        return "layout";
    }

    @PostMapping("/admin/delete/{id}")
    public ModelAndView deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);

        return new ModelAndView("redirect:/admin?deleted");
    }

    @GetMapping("/admin/add")
    public String addNewBookPage(Model model) {
        model.addAttribute("title", "Добавить книгу");
        model.addAttribute("view", "addBook");
        model.addAttribute("book", new BookDto());

        return "layout";
    }

    @PostMapping("/admin/add")
    public ModelAndView addNewBook(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("book") @Valid BookDto book,
            BindingResult result, WebRequest request, Errors errors
    ) {
        if (file.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("layout", "book", book);
            modelAndView.addObject("view", "addBook");
            modelAndView.addObject("title", "Добавить книгу");
            modelAndView.addObject("imgFail", true);

            return modelAndView;
        }

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("layout", "book", book);
            modelAndView.addObject("view", "addBook");
            modelAndView.addObject("title", "Добавить книгу");

            return modelAndView;
        } else {
            if (categoryRepository.findByName(book.getGenre()) == null) {
                categoryRepository.save(new Category(book.getGenre()));
            }

            bookRepository.save(
                    new Book(
                            book.getTitle(),
                            file.getOriginalFilename(),
                            book.getAuthor(),
                            book.getPublisher(),
                            book.getGenre(),
                            book.getDescription(),
                            book.getYear(),
                            book.getPrice(),
                            getRandomNumberInRange(0, 5)
                    )
            );
            storageService.uploadFile(file);

            return new ModelAndView("redirect:/admin/add?success");
        }
    }

    @GetMapping("/admin/edit/{id}")
    public String editBookPage(
            Model model,
            @PathVariable Integer id
    ) {
        Book book = bookRepository.findById(id).get();
        model.addAttribute("title", "Редактировать книгу");
        model.addAttribute("view", "addBook");
        model.addAttribute("book", book);
        model.addAttribute("bookId", id);

        return "layout";
    }

    @PostMapping("/admin/edit/{id}")
    public ModelAndView editBook(
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id,
            @ModelAttribute("book") @Valid BookDto book,
            BindingResult result, WebRequest request, Errors errors
    ) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("layout", "book", book);
            modelAndView.addObject("view", "addBook");
            modelAndView.addObject("title", "Редактировать книгу");

            return modelAndView;
        } else {
            Book oldBook = bookRepository.findById(id).get();
            oldBook.setTitle(book.getTitle());
            if (!file.isEmpty()) {
                oldBook.setImage(file.getOriginalFilename());
            }
            oldBook.setAuthor(book.getAuthor());
            oldBook.setPublisher(book.getPublisher());
            oldBook.setGenre(book.getGenre());
            oldBook.setDescription(book.getDescription());
            oldBook.setYear(book.getYear());
            oldBook.setPrice(book.getPrice());
            bookRepository.save(oldBook);
            try {
                storageService.uploadFile(file);
            } catch (StorageException e) {}

            return new ModelAndView("redirect:/admin?success");
        }
    }
}
