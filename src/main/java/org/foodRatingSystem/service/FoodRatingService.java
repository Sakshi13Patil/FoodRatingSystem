package org.foodRatingSystem.service;

import org.foodRatingSystem.model.Ratings;
import org.foodRatingSystem.model.Restaurant;

import java.util.List;

public interface FoodRatingService {

    void registerUser(String name, String gender , String phoneNumber, String pincode);
    void loginUser(String phoneNumber);
    void registerRestaurant(String name, String areas, String specialDish, Double price);
    List<Restaurant> showRestaurant(String sortBy);
    void createReview(String name, Integer rating,String comment);
    void showRating(String name);




}
