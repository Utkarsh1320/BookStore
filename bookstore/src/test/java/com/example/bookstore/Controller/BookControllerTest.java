//package com.example.bookstore.Controller;
//
//import com.example.bookstore.Model.Book;
//import com.example.bookstore.Repository.BookRepository;
//import com.example.bookstore.Service.BookService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDate;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BookControllerTest {
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private BookService bookService;
//
//    @InjectMocks
//    private BookController bookController;
//
//
//    @BeforeEach
//    void setUp(){
//        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
//
//        objectMapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Chain the calls
//    }
//
//    @Test
//    void createBook_shouldReturnCreateBook() throws Exception{
//        Book newBook = new Book(null, "Test Title", "1234567890", 25.00, LocalDate.of(2023,12,13));
//        Book savedBook = new Book(1L, "Test Title", "1234567890", 25.00, LocalDate.of(2023,12,13));
//
//        when(bookService.saveBook(any(Book.class))).thenReturn(savedBook);
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newBook)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("Test Title"))
//                .andExpect(jsonPath("$.isbn").value("1234567890"))
//                .andExpect(jsonPath("$.price").value(25.00))
//                .andExpect(jsonPath("$.publicationDate").value("2023-12-13"));
//    }
//}