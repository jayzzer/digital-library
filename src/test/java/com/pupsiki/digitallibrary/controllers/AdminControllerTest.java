package com.pupsiki.digitallibrary.controllers;

import com.pupsiki.digitallibrary.models.Book;
import com.pupsiki.digitallibrary.models.BookDto;
import com.pupsiki.digitallibrary.repositories.BookRepository;
import com.pupsiki.digitallibrary.repositories.UserRepository;
import com.pupsiki.digitallibrary.services.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdminController adminController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private StorageService storageService;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void admin() throws Exception {
        PageRequest pagerequest = PageRequest.of(1, 15,  Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(pagerequest);
        Mockito.when(bookRepository.findAll(any(PageRequest.class))).thenReturn(books);

        this.mockMvc.perform(get("/admin").
                param("p", "1")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("title", "Панель администратора")).
                andExpect(model().attribute("view", "adminPage")).
                andExpect(model().attribute("books", books));

        String response = this.restTemplate.getForObject("/admin", String.class);
        assertThat(response).contains("Все книги");
    }

    @Test
    public void addNewBookPage() throws Exception {
        this.mockMvc.perform(get("/admin/add")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(model().attribute("title", "Добавить книгу")).
                andExpect(model().attribute("view", "addBook"));

        String response=this.restTemplate.getForObject("/admin/add",String.class);
        assertThat(response).contains("GrayTeam 2019");
    }

    @Test
    public void addNewBook_added() throws Exception {
        MultipartFile file = new MockMultipartFile("not_empty_file.txt", new FileInputStream(new File("src/main/upload/not_empty_file.txt")));
        BookDto bookDto = new BookDto();
        BindingResult result = Mockito.mock(BindingResult.class);
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Errors errors = Mockito.mock(Errors.class);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/add?success");
        storageService.uploadFile(file);
        assertEquals(modelAndView.getViewName(),
                adminController.addNewBook(file,bookDto, result, webRequest, errors).getViewName());
    }

    @Test
    public void addNewBook_fileIsEmpty() throws Exception {
        MultipartFile file = new MockMultipartFile("empty_file.txt", new FileInputStream(new File("src/main/upload/empty_file.txt")));
        BookDto bookDto = new BookDto();
        BindingResult result = Mockito.mock(BindingResult.class);
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Errors errors = Mockito.mock(Errors.class);

        ModelAndView modelAndView = new ModelAndView("layout", "book", bookDto);
        modelAndView.addObject("view", "addBook");
        modelAndView.addObject("title", "Добавить книгу");
        modelAndView.addObject("imgFail", true);

        assertEquals(modelAndView.getModel(),
                adminController.addNewBook(file,bookDto, result, webRequest, errors).getModel());
    }


    @Test
    public void editBookPage() throws Exception {
//        Book book = new Book("bookname", "book1.jpg", "author", "romashka", "genre", "dwacr", 1999, 235, 1234f);
//        book.setId(123);
//        //Mockito.when(bookRepository.findById(anyInt()).get()).thenReturn(book);
//        this.mockMvc.perform(get("/admin/edit/{id}", book.getId())).
//                andDo(print()).
//                andExpect(status().isOk()).
//                andExpect(model().attribute("title", "Редактировать книгу")).
//                andExpect(model().attribute("view", "addBook")).
//                andExpect(model().attribute("book", book)).
//                andExpect(model().attribute("bookId", book.getId()));

        String response=this.restTemplate.getForObject("/admin/edit/1",String.class);
        assertThat(response).contains("GrayTeam 2019");

    }

    @Test
    public void deleteBook() throws Exception {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin?deleted");
        assertEquals(modelAndView.getViewName(), adminController.deleteBook(anyInt()).getViewName());
    }

    @Test
    public void editBook() throws Exception {
    }
}