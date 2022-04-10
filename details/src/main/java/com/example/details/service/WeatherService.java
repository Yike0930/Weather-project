package com.example.details.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WeatherService {
    List<Integer> findCityIdByName(String city);
    Map<String, Map> findCityNameById(int id);
    Map<Integer, Map<String, Map>> findCitiesWeatherByName(List<String> cities);
//    List<Integer> findCitiesIdByName(List<String> cities);
//    Map<Integer, Map<String, Map>> findCitiesNameById(List<Integer> ids);
}
