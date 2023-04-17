package com.example.booklibrary.booklibrary.controller;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.booklibrary.booklibrary.entity.Book;
import com.example.booklibrary.booklibrary.service.BookServiceImpl;


@Controller
@CrossOrigin("*")
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;

	/*
	 * @PostMapping("/book") public ResponseEntity<Book> addBook(@RequestBody Book
	 * book) { Book savedBook = bookServiceImpl.addBook(book); return new
	 * ResponseEntity<>(savedBook, HttpStatus.CREATED);
	 * 
	 * }
	 */
    @PostMapping("/books")
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book>book) {
       List<Book> savedBook = bookServiceImpl.addBooks(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
   
    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") Long id) {
        bookServiceImpl.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    		
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookServiceImpl.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBookNameById(@PathVariable("id") Long id, @RequestBody Book book) {
        Optional<Book> bookData = Optional.ofNullable(bookServiceImpl.getBookById(id));

        if (bookData.isPresent()) {
            Book savedBook = bookData.get();
            savedBook.setName(book.getName());
            Book updatedBook = bookServiceImpl.update(book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = (List<Book>) bookServiceImpl.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

	public ResponseEntity<Book> update(Long id, Book updatedBook) {
		// TODO Auto-generated method stub
		return null;
	}
}

