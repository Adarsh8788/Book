package com.example.booklibrary.booklibrary.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.booklibrary.booklibrary.entity.Book;
import com.example.booklibrary.booklibrary.service.BookServiceImpl;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookServiceImpl bookServiceImpl;

    private Book book;
    private List<Book> books;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setName("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        books = new ArrayList<>();
        books.add(book);
    }

    @Test
    public void testAddBooks() {
        when(bookServiceImpl.addBooks(books)).thenReturn(books);
        ResponseEntity<List<Book>> response = bookController.addBooks(books);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(books);
        verify(bookServiceImpl, times(1)).addBooks(books);
    }
    @Test
    public void testGetBookById() {
        when(bookServiceImpl.getBookById(1L)).thenReturn(book);
        ResponseEntity<Book> response = bookController.getBookById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);
        verify(bookServiceImpl, times(1)).getBookById(1L);
    }

    @Test
    public void testUpdateBookNameById() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setName("The Catcher in the Rye");
        updatedBook.setAuthor("J.D. Salinger");
        when(bookServiceImpl.getBookById(1L)).thenReturn(book);
        when(bookServiceImpl.updateById(updatedBook)).thenReturn(updatedBook);
        ResponseEntity<Book> response = bookController.updateBookNameById(1L, updatedBook);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedBook);
        verify(bookServiceImpl, times(1)).getBookById(1L);
        verify(bookServiceImpl, times(1)).updateById(updatedBook);
    }

    @Test
    public void testGetAllBooks() {
        when(bookServiceImpl.getAllBooks()).thenReturn(books);
        ResponseEntity<List<Book>> response = bookController.getAllBooks();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(books);
        verify(bookServiceImpl, times(1)).getAllBooks();
    }
}
