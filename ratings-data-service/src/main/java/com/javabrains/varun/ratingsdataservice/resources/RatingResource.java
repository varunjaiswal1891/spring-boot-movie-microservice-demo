package com.javabrains.varun.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javabrains.varun.ratingsdataservice.models.Rating;
import com.javabrains.varun.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
    
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId)
    {
        return new Rating(movieId, 6);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId)
    {
         //get all rated movies first
        List<Rating> ratingList =  Arrays.asList(
                                        new Rating("100", 3),
                                        new Rating("200", 5)
                                    );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratingList);
        return userRating;
    }

    
}
