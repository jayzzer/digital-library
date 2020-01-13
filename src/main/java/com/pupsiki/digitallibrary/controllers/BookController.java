package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.*;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.DealRepository;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import com.pupsiki.digitallibrary.services.BookService;
import com.pupsiki.digitallibrary.services.CategoryService;
import com.pupsiki.digitallibrary.services.StripeService;
import org.hibernate.search.exception.EmptyQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    DealRepository dealRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StripeService stripeService;
    @Autowired
    BookService bookService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @GetMapping("/books/{id}")
    public String book(Model model, @PathVariable Integer id, Authentication authentication) {
        Book book = bookRepository.findById(id).get();

        boolean alreadyBought = false;
        if (authentication != null) {
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            List<Integer> userBooks = dealRepository.getUserBooks(user.getId());

            if (userBooks.contains(id)) {
                alreadyBought = true;
            }
        }
        model.addAttribute("view", "book");
        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);
        model.addAttribute("alreadyBought", alreadyBought);
        model.addAttribute("stripePublicKey", stripePublicKey);

        return "layout";
    }

    @GetMapping("/books/search")
    public String searchBooks(
            Model model,
            @RequestParam(name = "text", required = true, defaultValue = "") String text,
            @RequestParam(name = "p", required = true, defaultValue = "0") int pageIndex
    ) {
        Page<Book> books;
        try {
            books = bookService.findBooks(text, pageIndex, 15);
        } catch (EmptyQueryException e) {
            books = new PageImpl<>(new ArrayList<>());
        }

        model.addAttribute("view", "search");
        model.addAttribute("title", "Поиск");
        model.addAttribute("books", books);

        return "layout";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<Book> searchBooksJSON(@RequestParam(name = "text", required = true, defaultValue = "") String text) {
        try {
            return bookService.findBooks(text, 0, 5).getContent();
        } catch (EmptyQueryException e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public ModelAndView buyBook(HttpServletRequest request, Authentication authentication) throws Exception {
        Double amount = Double.parseDouble(request.getParameter("amount"));
        Integer bookId = Integer.parseInt(request.getParameter("bookId"));
        Book book = bookRepository.findById(bookId).get();
        if (amount > 0) {
            String token = request.getParameter("stripeToken");
            stripeService.chargeNewCard(token, amount, book.getTitle());
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).get();

        dealRepository.save(new Deal(user, book));

        return new ModelAndView("redirect:/books/" + bookId + "?success");
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
