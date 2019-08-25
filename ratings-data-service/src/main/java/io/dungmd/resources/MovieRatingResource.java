package io.dungmd.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dungmd.models.Rating;
import io.dungmd.models.UserRatings;

@RestController
@RequestMapping("/ratingsdata")
public class MovieRatingResource {
    
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRatings getRatingByUserId(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
            new Rating("550", 4),
            new Rating("551", 3)
        );
        return new UserRatings(ratings);
    }
    
}
