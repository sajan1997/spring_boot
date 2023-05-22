package com.example.spring6mvc.services;

import com.example.spring6mvc.domian.Book;

public interface BookService {

    Iterable<Book> findAll();

}
