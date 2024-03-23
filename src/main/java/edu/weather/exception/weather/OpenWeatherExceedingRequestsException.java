package edu.weather.exception.weather;

public class OpenWeatherExceedingRequestsException extends RuntimeException {
    public OpenWeatherExceedingRequestsException(String message) {
        super(message);
    }
}
