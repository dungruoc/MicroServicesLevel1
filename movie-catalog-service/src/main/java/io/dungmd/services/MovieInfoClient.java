package io.dungmd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.dungmd.models.CatalogItem;
import io.dungmd.models.Movie;
import io.dungmd.models.Rating;

@Service
public class MovieInfoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    // This does not have effect
    @HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
    public CatalogItem getMovieInfo(Rating rating) {
        // Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
        Movie movie = webClientBuilder.build()
                .get()
                .uri("http://movie-info-service/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackMovieInfo(Rating rating) {
        return new CatalogItem("Unknown", "", rating.getRating());
    }

}
