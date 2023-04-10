package com.example.booklibrary.booklibrary.service;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.booklibrary.booklibrary.entity.Book;
import com.example.booklibrary.booklibrary.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        logger.info("Retrieved all books from the database");
        return books;
    }

    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            logger.error("Book with ID {} not found", id);
            throw new BookNotFoundException("Book not found");
        }
        logger.info("Retrieved book with ID {} from the database", id);
        return book;
    }

    public Book addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        logger.info("Saved book with ID {} to the database", savedBook.getId());
        return savedBook;
    }
    
    public List<Book>addBooks(List<Book>books){
    	List<Book>savedBooks= new ArrayList<>();
    	for(Book book:books) {
    		savedBooks.add(bookRepository.save(book));
    }
    return savedBooks;
}

    public Book update(Book book) {
        Book updatedBook = bookRepository.save(book);
        logger.info("Updated book with ID {} in the database", updatedBook.getId());
        return updatedBook;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        logger.info("Deleted book with ID {} from the database", id);
    }
}

