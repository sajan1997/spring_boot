package com.example.spring6mvc.repository;

import com.example.spring6mvc.domian.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Long> {

}
