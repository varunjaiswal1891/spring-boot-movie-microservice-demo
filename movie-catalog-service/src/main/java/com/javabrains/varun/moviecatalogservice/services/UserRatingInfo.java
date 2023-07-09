package com.javabrains.varun.moviecatalogservice.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.javabrains.varun.moviecatalogservice.models.Rating;
import com.javabrains.varun.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {
    
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
                    commandProperties = {
                                 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
                                 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"),
                                 @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                                 @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
    })
    public UserRating getUserRating(@PathVariable("userId") String userId)
    {
        //get all user rated movies first
        //UserRating ratingList =  restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable("userId") String userId)
    {
        UserRating userRating = new UserRating();
        ArrayList<Rating> al = new ArrayList<>();
        al.add(new Rating("0", 0));
        userRating.setUserRating(al);

        return userRating;
    }


}
