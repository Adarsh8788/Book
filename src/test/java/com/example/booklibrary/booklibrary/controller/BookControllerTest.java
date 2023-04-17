package com.example.booklibrary.booklibrary.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.booklibrary.booklibrary.entity.Book;
import com.example.booklibrary.booklibrary.service.BookNotFoundException;
import com.example.booklibrary.booklibrary.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetAllBooks() throws Exception {
		// Arrange
		Book book1 = new Book();
		book1.setId(1L);
		book1.setName("Book 1");
		book1.setAuthor("Author 1");
		book1.setPublisher("Publisher 1");

		Book book2 = new Book();
		book2.setId(2L);
		book2.setName("Book 2");
		book2.setAuthor("Author 2");
		book2.setPublisher("Publisher 2");

		List<Book> books = Arrays.asList(book1, book2);
		when(bookService.getAllBooks()).thenReturn(books);

		// Act
		ResultActions resultActions = mockMvc.perform(get("/books"));

		// Assert
		resultActions.andExpect(status().isOk());
		assertThat(resultActions.andReturn().getResponse().getContentAsString())
			.isEqualTo(objectMapper.writeValueAsString(books));
	}

	@Test
	public void testGetBookById() throws Exception {
		// Arrange
		Book book = new Book();
		book.setId(1L);
		book.setName("Book 1");
		book.setAuthor("Author 1");
		book.setPublisher("Publisher 1");

		when(bookService.getBookById(1L)).thenReturn(book);

		// Act
		ResultActions resultActions = mockMvc.perform(get("/books/1"));

		// Assert
		resultActions.andExpect(status().isOk());
		assertThat(resultActions.andReturn().getResponse().getContentAsString())
			.isEqualTo(objectMapper.writeValueAsString(book));
	}

	@Test
	public void testGetBookByIdNotFound() throws Exception {
		// Arrange
		when(bookService.getBookById(1L)).thenThrow(new BookNotFoundException("Book not found"));

		// Act
		ResultActions resultActions = mockMvc.perform(get("/books/1"));

		// Assert
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void testAddBook() throws Exception {
		// Arrange
		Book book = new Book();
		book.setName("Book 1");
		book.setAuthor("Author 1");
		book.setPublisher("Publisher 1");

		when(bookService.addBook(book)).thenReturn(book);

		// Act
		ResultActions resultActions = mockMvc.perform(post("/books")
			.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(book))); 
	// Assert
	resultActions.andExpect(status().isOk());
	assertThat(resultActions.andReturn().getResponse().getContentAsString())
		.isEqualTo(objectMapper.writeValueAsString(book));
}

@Test
public void testUpdateBook() throws Exception {
	// Arrange
	Book book = new Book();
	book.setId(1L);
	book.setName("Book 1");
	book.setAuthor("Author 1");
	book.setPublisher("Publisher 1");

	when(bookService.update(book)).thenReturn(book);

	// Act
	ResultActions resultActions = mockMvc.perform(put("/books/1")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(book)));

	// Assert
	resultActions.andExpect(status().isOk());
	assertThat(resultActions.andReturn().getResponse().getContentAsString())
		.isEqualTo(objectMapper.writeValueAsString(book));
}

@Test
public void testDeleteBook() throws Exception {
	// Arrange
	when(bookService.deleteBookById(1L)).thenReturn(true);

	// Act
	ResultActions resultActions = mockMvc.perform(delete("/books/1"));

	// Assert
	resultActions.andExpect(status().isOk());
}
}

