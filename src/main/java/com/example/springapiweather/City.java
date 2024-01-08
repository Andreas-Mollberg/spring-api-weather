package com.example.springapiweather;

import jakarta.persistence.*;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String weather;


    public City() {
    }

    public City(String name, String weather) {
        this.name = name;
        this.weather = weather;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeather() {
        return weather;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weather='" + weather + '\'';
    }
}