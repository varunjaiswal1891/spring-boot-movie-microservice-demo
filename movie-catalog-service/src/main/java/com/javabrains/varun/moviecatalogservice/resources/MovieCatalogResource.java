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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
    {
        //get all rated movies first
        List<Rating> ratingList =  Arrays.asList(
            new Rating("12345", 3),
            new Rating("6789", 5)
        );
        //for each movieID call movie Info Service and get details
        //put them all together
        return ratingList.stream().map( r1 -> {
            
        //Use of RestTemplate which is going to be deprecated in future - using WebClient in future
        Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+r1.getMovieId(), Movie.class);
            
           //Use of WebClient to call any RestAPI   -- use of Async programming 
           /* 
           Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/"+r1.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();   // this block() call is waiting untill this asynchronous call does not return response          
            */
            return new CatalogItem(movie.getMovieName(),"test desccription11",r1.getRating());
        })
        .collect(Collectors.toList());

    }
}
