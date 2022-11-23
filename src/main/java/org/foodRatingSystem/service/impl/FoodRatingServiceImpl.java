package org.foodRatingSystem.service.impl;

import org.foodRatingSystem.exception.FoodRatingSystemExceptions;
import org.foodRatingSystem.model.Ratings;
import org.foodRatingSystem.model.Restaurant;
import org.foodRatingSystem.model.User;
import org.foodRatingSystem.repository.FoodRatingRepository;
import org.foodRatingSystem.service.FoodRatingService;

import java.util.*;
import java.util.stream.Collectors;

import static org.foodRatingSystem.constants.RestaurantSort.PRICE;
import static org.foodRatingSystem.constants.RestaurantSort.RATING;
import static org.foodRatingSystem.utils.FoodRatingUtils.validateRating;

public class FoodRatingServiceImpl implements FoodRatingService {

    private final FoodRatingRepository foodRatingRepository;

    public FoodRatingServiceImpl(FoodRatingRepository foodRatingRepository) {
        this.foodRatingRepository = foodRatingRepository;
    }


    @Override
    public void registerUser(String name, String gender, String phoneNumber, String pincode) {
        if (Objects.nonNull(phoneNumber)) {
            User user = User.builder()
                    .name(name)
                    .gender(gender)
                    .phoneNumber(phoneNumber)
                    .pincode(pincode.toUpperCase())
                    .build();
            if (!foodRatingRepository.registerUser(user))
                throw new FoodRatingSystemExceptions("User not saved for " + phoneNumber);
        }
    }

    @Override
    public void loginUser(String phoneNumber) {
        Optional<User> userOptional = foodRatingRepository.fetchUserByPhoneNo(phoneNumber);
        if (userOptional.isEmpty())
            throw new FoodRatingSystemExceptions("User does not exist for " + phoneNumber);
        else {
            foodRatingRepository.setRequestContext(userOptional.get());
            System.out.println("Logged In User" + userOptional.get());
        }

    }

    @Override
    public void registerRestaurant(String name, String areas, String specialDish, Double price) {
        List<String> pincodes = Arrays.stream(areas.toUpperCase().split("/")).toList();
        Restaurant restaurant = Restaurant.builder()
                .name(name)
                .areas(pincodes)
                .specialDish(specialDish)
                .dishPrice(price)
                .ratings(new ArrayList<>())
                .avgRating(0.00)
                .build();
        foodRatingRepository.registerRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> showRestaurant(String sortBy) {
        List<Restaurant> restaurants = new ArrayList<>();
        if (PRICE.name().equals(sortBy)) {
            restaurants = foodRatingRepository.showRestaurantsByPriceDesc();
        } else if (RATING.name().equals(sortBy)) {
            restaurants = foodRatingRepository.showRestaurantsByRatingsDesc();
        } else
            throw new FoodRatingSystemExceptions(sortBy + "Not Supported By System");

        System.out.println(restaurants);
        return restaurants;
    }

    @Override
    public void createReview(String name, Integer rating, String comment) {
        if (validateRating(rating)) {
            Ratings restaurantRating = Ratings.builder()
                    .rating(rating)
                    .review(comment)
                    .build();
            foodRatingRepository.rateRestaurant(restaurantRating, name);
        } else
            throw new FoodRatingSystemExceptions("Please give rating in range of 1 to 5");
    }

    @Override
    public void showRating(String name) {
        System.out.println(foodRatingRepository.showRatingsForRestaurant(name));
    }
}
