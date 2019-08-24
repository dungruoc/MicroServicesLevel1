package io.dungmd.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.modeler.ParameterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sun.javadoc.ParameterizedType;

import io.dungmd.models.CatalogItem;
import io.dungmd.models.Movie;
import io.dungmd.models.Rating;
import io.dungmd.models.UserRatings;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        // Get all rated movies by userId
        UserRatings userRatings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRatings.class);
        
        // For each movieId, call info service to get details
        return userRatings.getUserRatings().stream().map(rating -> {
           // Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
           Movie movie = webClientBuilder.build()
                   .get()
                   .uri("http://localhost:8082/movies/" + rating.getMovieId())
                   .retrieve()
                   .bodyToMono(Movie.class)
                   .block();
           return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
    }
}
