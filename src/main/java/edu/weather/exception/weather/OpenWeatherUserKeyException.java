package edu.weather.exception.weather;

public class OpenWeatherUserKeyException extends RuntimeException {
    public OpenWeatherUserKeyException(String message) {
        super(message);
    }
}