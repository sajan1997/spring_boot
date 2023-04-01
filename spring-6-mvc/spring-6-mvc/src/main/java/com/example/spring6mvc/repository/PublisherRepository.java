package com.example.spring6mvc.repository;

import com.example.spring6mvc.domian.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher,Long> {

}
