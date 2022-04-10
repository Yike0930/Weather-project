package com.example.details.service;

import com.example.details.config.EndpointConfig;
import com.example.details.exception.ResourceNotFoundException;
import com.example.details.pojo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherServiceImpl(RestTemplate getRestTemplate) {
        this.restTemplate = getRestTemplate;
    }

    @Override
    @Retryable(include = IllegalAccessError.class)
    public List<Integer> findCityIdByName(String city) {
        City[] cities = restTemplate.getForObject(EndpointConfig.queryWeatherByCity + city, City[].class);
        if(cities.length == 0) {
            throw new ResourceNotFoundException("We can't find this city " + city);
        }
        List<Integer> ans = new ArrayList<>();
        for(City c: cities) {
            if(c != null && c.getWoeid() != null) {
                ans.add(c.getWoeid());
            }
        }
        return ans;
    }

    @Override
    //change findcitynamebyid => find weather details by id
    public Map<String, Map> findCityNameById(int id) {
        Map<String, Map> ans = restTemplate.getForObject(EndpointConfig.queryWeatherById + id, HashMap.class);
        if(ans == null) {
            throw new ResourceNotFoundException("We can't find this id " + id);
        }
        return ans;
    }

    @Override
    public Map<Integer, Map<String, Map>> findCitiesWeatherByName(List<String> cities) {
        Map<Integer, Map<String, Map>> ans = new HashMap<>();
        for(String city : cities) {
            List<Integer> id = findCityIdByName(city);
            for(int i : id) {
                ans.put(i, findCityNameById(i));
            }
        }
        if(ans.size() != cities.size()) {
            throw new ResourceNotFoundException("We can't find one or more cities " + cities.toString());
        }
        return ans;
    }



}
