package com.karsa.controller;

import com.karsa.models.Movie;
import com.karsa.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Movie createMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Movie readMovieById(@PathVariable("id") String id) {
        return movieRepository.findById(id).orElse(new Movie());
    }

    /**
     * 根据一个或者多个属性查询单个结果
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/name/{name}")
    public Movie readMovieByName(@PathVariable("name") String name) {
        Movie movie = new Movie();
        movie.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "years", "gender", "birth", "director");
        Example<Movie> example = Example.of(movie, matcher);
        return movieRepository.findOne(example).orElse(new Movie());
    }

    /**
     * 根据一个或者多个属性查询单个结果
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/like/{name}")
    public List<Movie> listMovie(@PathVariable("name") String name) {
        Movie movie = new Movie();
        movie.setName(name);
        //ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("years", "birth");
        Example<Movie> example = Example.of(movie);
        return movieRepository.findAll(example);
    }


    /**
     * 根据一个或者多个属性查询单个结果
     *
     * @param years
     * @return
     */
    @GetMapping(value = "/years/{years}")
    public Movie readMovieByName(@PathVariable("years") Integer years) {
        Movie movie = new Movie();
        movie.setYears(years);
        Example<Movie> example = Example.of(movie);
        return movieRepository.findOne(example).orElse(new Movie());
    }

    /**
     * 根据一个或者多个属性查询单个结果
     *
     * @param years
     * @return
     */
    @GetMapping(value = "/years/count/{years}")
    public Movie countYears(@PathVariable("years") Integer years) {
        Movie movie = new Movie();
        movie.setYears(years);
        Example<Movie> example = Example.of(movie);
        return movieRepository.findOne(example).orElse(new Movie());
    }

    /**
     * 根据一个或者多个属性分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/page/{pageNumber}/pagesize/{pageSize}/name/{name}")
    public Page<Movie> readMoviesByPage(@PathVariable("pageNumber") int pageNumber,
                                        @PathVariable("pageSize") int pageSize, @PathVariable("name") String name) {
        Movie movie = new Movie();
        movie.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("years", "birth");
        Example<Movie> example = Example.of(movie, matcher);
        if (pageNumber < 1) {
            pageNumber = 1;
        } else if (pageSize == 0) {
            pageSize = 20;
        }

        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        return movieRepository.findAll(example, pageable);
    }

    /**
     * 根据用户年龄升序排序
     *
     * @return
     */
    @GetMapping("/read")
    public List<Movie> readMovies() {
        Sort sort = Sort.by(Order.asc("years"));
        return movieRepository.findAll(sort);
    }

    /**
     * 模糊查询带分页
     *
     * @param pageNumber
     * @param pageSize
     * @param keyWords
     * @return
     */
    @GetMapping(value = "/page/{pageNumber}/pagesize/{pageSize}/keyword/{keyWords}")
    public Page<Movie> readMoviesByKeywords(@PathVariable("pageNumber") int pageNumber,
                                            @PathVariable("pageSize") int pageSize, @PathVariable("keyWords") String keyWords) {
        if (keyWords == null) {
            keyWords = "";
        }
        if (pageNumber < 1) {
            pageNumber = 1;
        } else if (pageSize == 0) {
            pageSize = 20;
        }
        Sort sort = Sort.by(Order.asc("years"));
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return movieRepository.findByNameLike(keyWords, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void removeMovie(@PathVariable("id") String id) {
        movieRepository.deleteById(id);
    }
}