package org.foodRatingSystem;

import org.foodRatingSystem.repository.FoodRatingRepository;
import org.foodRatingSystem.service.FoodRatingService;
import org.foodRatingSystem.service.impl.FoodRatingServiceImpl;

public class Main {
    static FoodRatingRepository foodRatingRepository = new FoodRatingRepository();
    static FoodRatingService foodRatingService = new FoodRatingServiceImpl(foodRatingRepository);
    public static void main(String[] args) {

        foodRatingService.registerUser("Pralove", "M", "phoneNumber-1", "HSR");
        foodRatingService.registerUser("Nitesh", "M", "phoneNumber-2", "BTM");
        foodRatingService.registerUser("Vatsal", "M", "phoneNumber-3", "BTM");

        foodRatingService.loginUser("phoneNumber-1");
        foodRatingService.registerRestaurant("Food Court-1","BTM/HSR","NI Thali",100.00);
        foodRatingService.registerRestaurant("Food Court-2","BTM","Burger",120.00);

        foodRatingService.loginUser("phoneNumber-2");
        foodRatingService.registerRestaurant("Food Court-3","HSR","SI Thali",150.00);

        foodRatingService.loginUser("phoneNumber-3");
        foodRatingService.showRestaurant("PRICE");
        foodRatingService.createReview("Food Court-2",3,"Good Food");
        foodRatingService.createReview("Food Court-1",5,"Nice Food");
        foodRatingService.showRestaurant("RATING");
        foodRatingService.showRating("Food Court-2");

    }
}