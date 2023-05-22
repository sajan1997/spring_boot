package com.example.spring6mvc.services;

import com.example.spring6mvc.domian.Author;

public interface AuthorService {

    Iterable<Author> findAll();

}
