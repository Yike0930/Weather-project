package com.example.details.controller;

import com.example.details.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;

    @Value("${server.port}")
    private int randomServerPort;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/details")
    public ResponseEntity<?> queryWeatherByCity(@RequestParam(required = true) String city) {
        logger.info("Request city is "+ city);
        return new ResponseEntity<>(weatherService.findCityIdByName(city), HttpStatus.OK);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> queryWeatherByCity(@PathVariable int id) {
        logger.info("Request city id is "+ id);
        return new ResponseEntity<Map>(weatherService.findCityNameById(id), HttpStatus.OK);
    }

    @GetMapping("/details/cities")
    public ResponseEntity<?> getCitiesWeatherOfMultiCities(@RequestParam(required = true) List<String> cities) {
        logger.info("Request cities are "+ cities.toString());

        //TODO
        return new ResponseEntity<Map>(weatherService.findCitiesWeatherByName(cities), HttpStatus.OK);
    }

}
