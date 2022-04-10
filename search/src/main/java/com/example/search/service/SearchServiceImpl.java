package com.example.search.service;

import com.example.search.config.EndpointConfig;
import com.example.search.pojo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchServiceImpl implements SearchService{

    private final RestTemplate restTemplate;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    @Async
    @Retryable(include = IllegalAccessError.class)
    public CompletableFuture<List<Integer>> findCityIdByName(String city) {
        City c = restTemplate.getForObject(EndpointConfig.queryWeatherByCity + city, City.class);
        return CompletableFuture.completedFuture(c.getData());
    }

    @Override
    @Async
    public CompletableFuture<Map<String, Map>> findCityNameById(int id) {
        Map<String, Map> ans = restTemplate.getForObject(EndpointConfig.queryWeatherById + id, HashMap.class);
        return CompletableFuture.completedFuture(ans);
    }

    @Override
    @Async
    public CompletableFuture<Map<Integer, Map<String, Map>>> findCitiesWeatherByName(List<String> cities) {
//        Map<Integer, Map<String, Map>> ans = restTemplate.getForObject(EndpointConfig.queryWeatherDetailsByCities + cities, HashMap.class);
//        return ans;
        StringBuilder sb = new StringBuilder();
        sb.append(EndpointConfig.queryWeatherDetailsByCities);

        for(String city : cities) {
            sb.append(city);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        Map<Integer, Map<String, Map>> ans = restTemplate.getForObject(sb.toString(), HashMap.class);
        return CompletableFuture.completedFuture(ans);
    }
}
