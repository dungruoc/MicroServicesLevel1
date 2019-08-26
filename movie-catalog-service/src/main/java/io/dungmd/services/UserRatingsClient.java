package io.dungmd.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.dungmd.models.Rating;
import io.dungmd.models.UserRatings;

@Service
public class UserRatingsClient {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings")
    public UserRatings getUserRatings(String userId) {
        return restTemplate.getForObject("http://movie-ratings-service/ratingsdata/users/" + userId, UserRatings.class);
    }

    public UserRatings getFallbackUserRatings(String userId) {
        return new UserRatings(Arrays.asList());
    }

}
