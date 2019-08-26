package io.dungmd.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.dungmd.models.CatalogItem;
import io.dungmd.models.Movie;
import io.dungmd.models.Rating;
import io.dungmd.models.UserRatings;
import io.dungmd.services.MovieInfoClient;
import io.dungmd.services.UserRatingsClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Autowired
    MovieInfoClient movieInfoClient;
    
    @Autowired
    UserRatingsClient userRatingsClient;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        // Get all rated movies by userId       
        UserRatings userRatings = userRatingsClient.getUserRatings(userId);
        // For each movieId, call info service to get details
        return userRatings.getUserRatings().stream().map(rating -> movieInfoClient.getMovieInfo(rating)).collect(Collectors.toList());
    }

}
