package de.sebbecking.lieferandorestaurantscraper.votingmanager;

import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.RestaurantData;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Votes {
    public Map<String, List<String>> votes; // Restaurant-ID to List of Slack-Usernames
    public List<RestaurantData> restaurants;
    public boolean ended = false;
    public LocalTime endTime = null;

    public Votes(List<RestaurantData> restaurants){
        this.restaurants = restaurants;
        this.votes = new ConcurrentHashMap<>();
        restaurants.stream()
                .map(restaurantData -> restaurantData.id)
                .forEach(restaurantId -> this.votes.put(restaurantId, new LinkedList<String>()));
    }

    public void endVote(){
        this.ended = true;
    }

    public void toggleVoteForRestaurant(String restaurantId, String userId){
        List<String> usersForRestaurant = this.votes.get(restaurantId);
        if (usersForRestaurant.contains(userId)){
            usersForRestaurant.remove(userId);
        } else {
            usersForRestaurant.add(userId);
        }
    }

    public String getStringListOfVotersForRestaurant(String restaurantId){
        List<String> usersForRestaurant = this.votes.get(restaurantId);
        return String.join(", ", usersForRestaurant.stream().map(user -> "@" + user).collect(Collectors.toList()));
    }

    public int getNumberOfVotersForRestaurant(String restaurantId){
        return this.votes.get(restaurantId).size();
    }
}
