package com.example.springapiweather.controllers;

import com.example.springapiweather.City;
import com.example.springapiweather.Country;
import com.example.springapiweather.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/countries")
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<Iterable<Country>> getAllCountries() {
        try {
            return ResponseEntity.ok(countryRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<String> addNewCountry(@RequestParam String countryName, @RequestParam String cityName, @RequestParam String weather) {
        try {
            Country country = new Country(countryName);
            City city = new City(cityName, weather);
            country.getCities().add(city);

            countryRepository.save(country);
            return ResponseEntity.ok("Saved country");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping(path = "/update/{id}")
    public @ResponseBody ResponseEntity<String> updateCountry(
            @PathVariable Integer id, @RequestParam String name) {
        try {
            var optionalCountry = countryRepository.findById(id);

            if (optionalCountry.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found with id: " + id);
            }

            var country = optionalCountry.get();
            country.setName(name);
            countryRepository.save(country);

            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteCountry/{id}")
    public @ResponseBody ResponseEntity<String> deleteCountry(@PathVariable Integer id) {
        try {
            Optional<Country> country = countryRepository.findById(id);
            if (country.isPresent()) {
                countryRepository.delete(country.get());
                return ResponseEntity.ok("Deleted Country ID: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
