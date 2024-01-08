package com.example.springapiweather.controllers;

import com.example.springapiweather.City;
import com.example.springapiweather.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<City> read(@PathVariable int id) {
        return cityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public @ResponseBody ResponseEntity<City> create(@RequestBody City city) {
        City savedCity = cityRepository.save(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
    }

    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<String> updateCity(
            @PathVariable Integer id, @RequestParam String name, @RequestParam String weather) {
        try {
            var optionalCity = cityRepository.findById(id);

            if (optionalCity.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found with id: " + id);
            }

            var city = optionalCity.get();
            city.setName(name);
            city.setWeather(weather);
            cityRepository.save(city);

            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteCity/{id}")
    public @ResponseBody ResponseEntity<String> deleteCity(@PathVariable Integer id) {
        try {
            Optional<City> city = cityRepository.findById(id);
            if (city.isPresent()) {
                cityRepository.delete(city.get());
                return ResponseEntity.ok("Deleted City with ID: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}

