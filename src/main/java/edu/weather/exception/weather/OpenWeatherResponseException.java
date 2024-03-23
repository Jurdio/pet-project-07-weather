package edu.weather.exception.weather;

public class OpenWeatherResponseException extends RuntimeException {
    public OpenWeatherResponseException(String message) {
        super(message);
    }
}