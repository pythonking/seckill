package com.karsa.repository;

import com.karsa.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Page<Movie> findByNameLike(String keyWords, PageRequest pageable);
}
