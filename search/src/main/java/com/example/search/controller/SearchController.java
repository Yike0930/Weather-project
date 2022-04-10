package com.example.search.controller;

import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/weather/search")
    public CompletableFuture<ResponseEntity<?>> getDetails(@RequestParam(required = true) String city) {
        return searchService.findCityIdByName(city).thenApply(ResponseEntity :: ok);
    }

    @GetMapping("/weather/details/{id}")
    public CompletableFuture<ResponseEntity<?>> getWeatherDetails(@PathVariable int id) {
        return searchService.findCityNameById(id).thenApply(ResponseEntity :: ok);
    }

    @GetMapping("/weather/details")
    public CompletableFuture<ResponseEntity<?>> getCitiesWeatherOfMultiCities(@RequestParam(required = true) List<String> cities) {
        //TODO
        return searchService.findCitiesWeatherByName(cities).thenApply(ResponseEntity :: ok);
    }

    //I/O error?
//    @GetMapping("/weather/details")
//    public  ResponseEntity<?> getWeatherDetailsOfCities(@RequestParam(required = true) List<String> cities) {
//        return new ResponseEntity<>(searchService.findCitiesWeatherByName(cities),HttpStatus.OK);
//    }


}
