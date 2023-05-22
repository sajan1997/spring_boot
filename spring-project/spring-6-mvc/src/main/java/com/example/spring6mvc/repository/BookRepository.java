package com.example.spring6mvc.repository;

import com.example.spring6mvc.domian.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
}
