public class WeatherData {
    private String city;
    private String description;
    private double temperature;

    public WeatherData(String city, String description, double temperature) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "City: " + city + ", Description: " + description + ", Temperature: " + temperature + "°C";
    }
}
