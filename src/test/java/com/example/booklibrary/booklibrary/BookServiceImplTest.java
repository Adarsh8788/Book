package com.example.booklibrary.booklibrary.service;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.booklibrary.booklibrary.entity.Book;
import com.example.booklibrary.booklibrary.repository.BookRepository;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;
    private List<Book> bookList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        book1 = new Book(1, "Book 1", "Author 1", "Publisher 1");
        book2 = new Book(2, "Book 2", "Author 2", "Publisher 2");
        bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.getAllBooks();

        assertEquals(bookList, result);
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(book1));

        Book result = bookService.getBookById(1L);

        assertEquals(book1, result);
    }

    @Test
    void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
    }

    @Test
    void testAddBook() {
        when(bookRepository.save(book1)).thenReturn(book1);

        Book result = bookService.addBook(book1);

        assertEquals(book1, result);
    }


    @Test
    void testUpdate() {
        when(bookRepository.save(book1)).thenReturn(book1);

        Book result = bookService.updateById(book1);

        assertEquals(book1, result);
    }

    @Test
    void testDeleteBook() {
    	Long id= 1L;
        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

}
