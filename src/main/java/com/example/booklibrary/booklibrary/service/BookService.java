package com.example.booklibrary.booklibrary.service;

import java.util.List;

import com.example.booklibrary.booklibrary.entity.Book;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(Book book);
    Book update(Book book);
	Object deleteBookById(long l);
}
