package com.javabrains.varun.moviecatalogservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javabrains.varun.moviecatalogservice.models.CatalogItem;

import java.util.*;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
    {
        return Collections.singletonList(
            new CatalogItem("Matrix","desc1",4)
        );
    }
}
