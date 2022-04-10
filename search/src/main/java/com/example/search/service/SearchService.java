package com.example.search.service;

        import com.example.search.pojo.City;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.CompletableFuture;

@Service
public interface SearchService {
    CompletableFuture<List<Integer>> findCityIdByName(String city);
    CompletableFuture<Map<String, Map>> findCityNameById(int id);
    CompletableFuture<Map<Integer, Map<String, Map>>> findCitiesWeatherByName(List<String> cities);
}
