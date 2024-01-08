package com.example.springapiweather;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(String name, City city) {
        this.name = name;
        cities.add(city);
    }

    public void removeCity(int index) {
        cities.remove(index);
    }

    public List<City> getCities() {
        return cities;
    }

    public void getCityById(int id) {
        cities.get(id);
    }

    public void getAllCities() {
        cities.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
