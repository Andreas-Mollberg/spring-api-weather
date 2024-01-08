package com.example.springapiweather.repositories;

import com.example.springapiweather.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {

}
