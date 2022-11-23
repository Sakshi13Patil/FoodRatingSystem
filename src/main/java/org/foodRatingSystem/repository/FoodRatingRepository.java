package org.foodRatingSystem.repository;

import org.foodRatingSystem.exception.FoodRatingSystemExceptions;
import org.foodRatingSystem.model.Ratings;
import org.foodRatingSystem.context.RequestContext;
import org.foodRatingSystem.model.Restaurant;
import org.foodRatingSystem.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class FoodRatingRepository {

    List<User> registeredUsers = new ArrayList<>();
    //map < phoneNum, user>
    //retriving user object-> updating it with new phone
    //removing oldKey and adding new key
    RequestContext requestContext;
    List<Restaurant> registeredRestaurants = new ArrayList<>();


    public boolean registerUser(User user) {
        Optional<User> userOptional = fetchUserByPhoneNo(user.getPhoneNumber());
        if (userOptional.isPresent())
            throw new FoodRatingSystemExceptions("User already Exists with " + user.getPhoneNumber());
        else {
            return registeredUsers.add(user);
        }
    }


    public void setRequestContext(User user) {
        requestContext = RequestContext.builder()
                .loggedInUser(user)
                .build();
    }

    public boolean registerRestaurant(Restaurant restaurant) {
        if (Objects.isNull(requestContext))
            throw new FoodRatingSystemExceptions("Please login before registering a restaurent.");
        else {
            Optional<Restaurant> restaurantOptional = fetchRestaurantByName(restaurant.getName());
            if (restaurantOptional.isPresent())
                throw new FoodRatingSystemExceptions("Restaurant already Exists with " + restaurant.getName());
            else {
                restaurant.setCreatedBy(requestContext.getLoggedInUser().getPhoneNumber());
                return registeredRestaurants.add(restaurant);
            }
        }
    }

    public void rateRestaurant(Ratings rating, String restaurantName) {
        if (Objects.isNull(requestContext))
            throw new FoodRatingSystemExceptions("Please login before rating a restaurant.");
        else {
            addRatingToRestaurant(rating, restaurantName);
        }
    }

    private void addRatingToRestaurant(Ratings rating, String restaurantName) {
        Optional<Restaurant> restaurantOptional = fetchRestaurantByName(restaurantName);
        if (restaurantOptional.isEmpty())
            throw new FoodRatingSystemExceptions("No Restaurant Exists with name" + restaurantName);
        else {
            rating.setCreatedBy(requestContext.getLoggedInUser().getPhoneNumber());
            restaurantOptional.get().getRatings().add(rating);
            restaurantOptional.get()
                    .setAvgRating(getAvgRating(restaurantOptional.get().getRatings()));
        }
    }

    private double getAvgRating(List<Ratings> ratings) {
        int totalRating = 0;
        for (Ratings rating : ratings) {
            totalRating += rating.getRating();
        }
        if (ratings.size() != 0)
            return (double) totalRating / ratings.size();
        else
            return 0.00;
    }

    public List<Restaurant> showRestaurantsByPriceDesc() {
        if (Objects.isNull(requestContext))
            throw new FoodRatingSystemExceptions("Please login before fetching restaurants.");
        else {
            List<Restaurant> restaurants = fetchRestaurantByPinCode(requestContext.getLoggedInUser().getPincode());
            if (Objects.nonNull(restaurants)) {
                restaurants.sort(Comparator.comparing(Restaurant::getDishPrice));
                return restaurants;
            } else
                return new ArrayList<>();
        }
    }

    public List<Restaurant> showRestaurantsByRatingsDesc() {
        if (Objects.isNull(requestContext))
            throw new FoodRatingSystemExceptions("Please login before fetching restaurants.");
        else {
            List<Restaurant> restaurants = fetchRestaurantByPinCode(requestContext.getLoggedInUser().getPincode());
            if (Objects.nonNull(restaurants)) {
                restaurants.sort(Comparator.comparing(Restaurant::getAvgRating));
                return restaurants;
            }
            else
                return new ArrayList<>();
        }
    }

    public List<Ratings> showRatingsForRestaurant(String restaurantName) {
        Optional<Restaurant> restaurantOptional = fetchRestaurantByName(restaurantName);
        if (restaurantOptional.isEmpty())
            throw new FoodRatingSystemExceptions("No Restaurant Exists with name" + restaurantName);
        else {
            return restaurantOptional.get().getRatings();
        }
    }

    public Optional<User> fetchUserByPhoneNo(String phoneNumber) {
        return registeredUsers.stream().filter(user -> user.getPhoneNumber().equals(phoneNumber)).findFirst();
    }

    private Optional<Restaurant> fetchRestaurantByName(String name) {
        return registeredRestaurants.stream().filter(restaurant -> restaurant.getName().equals(name)).findFirst();
    }

    private List<Restaurant> fetchRestaurantByPinCode(String pincode) {
        return registeredRestaurants.stream().filter(restaurant -> restaurant.getAreas().contains(pincode)).collect(Collectors.toList());
    }

}

