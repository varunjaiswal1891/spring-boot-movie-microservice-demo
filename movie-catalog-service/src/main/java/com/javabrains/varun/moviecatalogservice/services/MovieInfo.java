package com.javabrains.varun.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javabrains.varun.moviecatalogservice.models.CatalogItem;
import com.javabrains.varun.moviecatalogservice.models.Movie;
import com.javabrains.varun.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;
    
    //setting bulkhead pattern - ship sink compartments concept
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
                    threadPoolKey = "movieInfoPool",
                    threadPoolProperties = {
                        @HystrixProperty(name = "coreSize",value = "20"),
                        @HystrixProperty(name = "maxQueueSize",value = "10")
                    })
    public CatalogItem getCatalogItem(Rating r1)
    {
        //Use of RestTemplate which is going to be deprecated in future - using WebClient in future
        //for each movieID call movie Info Service and get details
        //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+r1.getMovieId(), Movie.class);
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+r1.getMovieId(), Movie.class);
        //put them all together
        return new CatalogItem(movie.getTitle(),movie.getOverview(),r1.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating r1)
    {
        return new CatalogItem("Movie Not Found","movie.getOverview()",r1.getRating());
    }
}
