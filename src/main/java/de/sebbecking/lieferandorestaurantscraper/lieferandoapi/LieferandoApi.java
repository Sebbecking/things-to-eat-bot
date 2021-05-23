package de.sebbecking.lieferandorestaurantscraper.lieferandoapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.OperatingDateEnum;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.RestaurantData;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantList.RestaurantList;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;
import de.sebbecking.lieferandorestaurantscraper.util.Config;
import de.sebbecking.lieferandorestaurantscraper.util.Deserializers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LieferandoApi extends EventHandlerAware {
    static Logger logger = Logger.getLogger(LieferandoApi.class.getName());
    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    public LieferandoApi(EventHandler eventHandler){
        super(eventHandler);
    }

    private String makeGetRequest(String url) throws IOException, InterruptedException {
        logger.info("Sending GET request to " + url);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String makePostRequest(String url, Map<String, String> data) throws Exception {
        String encodedData = String.join("&", data.entrySet().stream().map(entry ->
                  java.net.URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                + java.net.URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)
        ).collect(Collectors.toList()));

        logger.info("Sending POST request to " + url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(encodedData))
                .header("Accept", "*/*")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Received answer with Status Code " + response.statusCode());
        if(response.body().length() == 0){
            throw new Exception("API Call returned empty!");
        }

        return response.body();
    }

    public RestaurantList getRestaurantsFromLocation(String plz, String lat, String lng){
        Map<String, String> parameterMap = new LinkedHashMap<>();
        parameterMap.put("var0", "32427ec1a96dfd5b47c1d6e9645b04bf"); // = md5("4ndro1d")
        parameterMap.put("var1", "getrestaurants");
        parameterMap.put("var2", plz);
        parameterMap.put("var3", "2");
        parameterMap.put("var4", lat);
        parameterMap.put("var5", lng);
        parameterMap.put("var6", "en");
        parameterMap.put("var7", "0");
        parameterMap.put("var8", "1");
        parameterMap.put("version", "5.32");
        parameterMap.put("systemversion", "30;7.3.17.3.1");
        parameterMap.put("appname", "Lieferando.de");
        parameterMap.put("language", "de");

        RestaurantList restaurants;
        try {
            String response = makePostRequest("https://de.citymeal.com/android/android.php/?", parameterMap);
            restaurants = Deserializers.xmlMapper.readValue(response, RestaurantList.class);
        } catch  (Exception e) {
            logger.severe("Error while getting/parsing Restaurants for location.");
            logger.severe(e.getMessage());
            restaurants = new RestaurantList();
        }

        return restaurants;
    }

    public RestaurantData getRestaurantData(String id){
        RestaurantData restaurantData;
        try {
            String response = this.makeGetRequest(String.format("https://de-cdn.citymeal.com/ws/6.0.8/getrestaurantdata/%s/de", id));
            restaurantData = Deserializers.xmlMapper.readValue(response, RestaurantData.class);
        } catch (Exception e) {
            logger.severe("Error while getting/parsing Restaurant Data for Restaurant with id " + id);
            logger.severe(e.getMessage());
            restaurantData = new RestaurantData();
        }
        return restaurantData;
    }

    public List<RestaurantData> getRestaurantsOpenTodayAtLunchTime(){
        LocalTime lunchTime = LocalTime.parse(Config.get("LUNCH_TIME"), Config.timeFormat);
        logger.info("Get all Restaurants for Location");
        List<RestaurantData> possibleRestaurants = this.getRestaurantsFromLocation(Config.get("PLZ"), Config.get("LAT"), Config.get("LONG")).restaurantList.stream()
                .map(restaurantEntry -> this.getRestaurantData(restaurantEntry.id))
                .collect(Collectors.toList());
        logger.info("Retrieved " + possibleRestaurants.size() + " restaurants");

        logger.info("Filter Restaurants that are open at " + lunchTime.format(Config.timeFormat) + " today");
        OperatingDateEnum today = OperatingDateEnum.forCode(LocalDateTime.now().getDayOfWeek().getValue() % 7);
        return possibleRestaurants.stream()
                .filter(restaurant -> restaurant.deliveryTime != null && restaurant.deliveryTime.timeWindows != null)
                .filter(restaurant -> {
                    if(restaurant.deliveryExceptions != null && restaurant.deliveryExceptions.getForOperatingDate(today).isPresent()){
                        return restaurant.deliveryExceptions.getForOperatingDate(today).get().timeInWindows(lunchTime);
                    }
                    return restaurant.deliveryTime.getForOperatingDate(today)
                        .map(timeWindow -> timeWindow.timeInWindows(lunchTime))
                        .orElse(false);
                }).collect(Collectors.toList());
    }
}
