package io.dungmd.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.dungmd.models.CatalogItem;
import io.dungmd.models.Movie;
import io.dungmd.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        // Get all rated movies by userId
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("1235", 3)
        );
        
        // For each movieId, call info service to get details
        return ratings.stream().map(rating -> {
           Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
           return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
    }
}
