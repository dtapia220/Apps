import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {
    private static final String API_KEY = "14ca7bf8608f3f67c9371f3c3ad335d2"; // Replace with your actual API key
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid="
            + API_KEY;

    public static WeatherData fetchWeather(String city) {
        try {
            String urlString = String.format(API_URL, city);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            // Parse the JSON response manually
            String jsonString = content.toString();
            String description = parseJsonValue(jsonString, "description");
            double temperature = Double.parseDouble(parseJsonValue(jsonString, "temp"));

            return new WeatherData(city, description, temperature);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseJsonValue(String jsonString, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = jsonString.indexOf(searchKey) + searchKey.length();
        int endIndex = jsonString.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = jsonString.indexOf("}", startIndex);
        }
        String value = jsonString.substring(startIndex, endIndex).replaceAll("[\"{}]", "").trim();
        return value;
    }

    public static void main(String[] args) {
        WeatherData weather = fetchWeather("London");
        if (weather != null) {
            System.out.println("City: " + weather.getCity());
            System.out.println("Description: " + weather.getDescription());
            System.out.println("Temperature: " + weather.getTemperature());
        }
    }
}