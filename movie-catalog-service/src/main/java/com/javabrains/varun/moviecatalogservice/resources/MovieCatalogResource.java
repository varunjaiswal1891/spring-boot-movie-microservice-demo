package com.javabrains.varun.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.javabrains.varun.moviecatalogservice.models.CatalogItem;
import com.javabrains.varun.moviecatalogservice.models.Movie;
import com.javabrains.varun.moviecatalogservice.models.Rating;
import com.javabrains.varun.moviecatalogservice.models.UserRating;
import com.javabrains.varun.moviecatalogservice.services.MovieInfo;
import com.javabrains.varun.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
    {
        
        UserRating ratingList = userRatingInfo.getUserRating(userId);
        return ratingList.getUserRating()
                         .stream()
                         .map( r1 -> movieInfo.getCatalogItem(r1))
                         .collect(Collectors.toList());
    }

    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId)
    {
        return Arrays.asList(new CatalogItem("No Movie", "", 0));
    }

   

    

    
}

//Use of WebClient to call any RestAPI   -- use of Async programming 
           /* 
           Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/"+r1.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();   // this block() call is waiting untill this asynchronous call does not return response          
            */
