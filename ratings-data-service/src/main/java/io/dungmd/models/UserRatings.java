package io.dungmd.models;

import java.util.List;

public class UserRatings {
    
    private List<Rating> userRatings;

    public UserRatings(List<Rating> ratings) {
        this.userRatings = ratings;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }
    
    

}
