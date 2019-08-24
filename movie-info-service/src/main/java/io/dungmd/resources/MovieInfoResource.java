package io.dungmd.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dungmd.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieInfoResource {

    @RequestMapping("/{movieId}")
    public Movie getMovieById(@PathVariable("movieId") String movieId) {
        return new Movie(movieId, "Movie Name", "Description");
    }
}
