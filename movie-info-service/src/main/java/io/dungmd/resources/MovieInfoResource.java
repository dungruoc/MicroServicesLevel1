package io.dungmd.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.dungmd.model.Movie;
import io.dungmd.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieInfoResource {

    @Value("${api.key}")
    private String apiKey;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/{movieId}")
    public Movie getMovieById(@PathVariable("movieId") String movieId) {
        // https://api.themoviedb.org/3/movie/550?api_key=0bbcf738392f1a6443c46b37fce855b5
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
