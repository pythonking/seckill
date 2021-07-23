package com.karsa.repository;

import com.karsa.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    /**
     * 模糊搜索
     *
     * @param keyWords
     * @param pageable
     * @return
     */
    Page<Movie> findByNameLike(String keyWords, PageRequest pageable);
}
