package com.example.booklibrary.booklibrary.repository;
import com.example.booklibrary.booklibrary.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	Book save(String book);

}

