import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherNetworking {
    public static WeatherModel getWeatherForZip(String zipCode) {
        String baseURL = "http://api.weatherapi.com/v1";
        String endPoint = "/current.json";
        String apiKey = "dfc37821221644c1a8520344252105";
        String url = baseURL + endPoint + "?key=" + apiKey + "&q=" + zipCode;
        String urlResponse = "";
        try {
            URI myUri = URI.create(url);
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // create JSON object from response
        JSONObject jsonObj = new JSONObject(urlResponse);

        // parse current weather info
        JSONObject currentObj = jsonObj.getJSONObject("current");
        double tempC = currentObj.getDouble("temp_c");
        double tempF = currentObj.getDouble("temp_f");
        JSONObject conditionObj = currentObj.getJSONObject("condition");
        String condition = conditionObj.getString("text");
        String iconURL = conditionObj.getString("icon");
        iconURL = "https:" + iconURL;

        // parse location info
        JSONObject locationObj = jsonObj.getJSONObject("location");
        String location = locationObj.getString("name");

        // create and return Weather object
        WeatherModel weather = new WeatherModel(tempC, tempF, condition, iconURL, location);
        return weather;
    }

    public static WeatherModel getForeCastZip(String zipCode) {
        String urlResponse="";
        try {
            URI myUri = URI.create("http://api.weatherapi.com/v1/forecast.json?key=dfc37821221644c1a8520344252105&q="+zipCode+"&days=2");
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        double minTempC = 0.0;
        double minTempF = 0.0;
        double maxTempC = 0.0;
        double maxTempF = 0.0;
        String date="";
        JSONObject jsonObj = new JSONObject(urlResponse);
        JSONObject currentObj = jsonObj.getJSONObject("forecast");
        JSONArray array= currentObj.getJSONArray("forecastday");
            JSONObject x = array.getJSONObject(1);
        minTempC = x.getDouble("mintemp_c");
        minTempF = x.getDouble("mintemp_f");
        maxTempC = x.getDouble("maxtemp_c");
        maxTempF = x.getDouble("maxtemp_f");
        date= x.getString("date");
        JSONObject locationObj = x.getJSONObject("location");
        String location = locationObj.getString("name");
        WeatherModel weather = new WeatherModel(minTempC,minTempF,maxTempC,maxTempF,location,date);
        return weather;
    }


}



