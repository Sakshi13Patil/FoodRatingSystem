package org.foodRatingSystem.utils;

public class FoodRatingUtils {

    public static boolean validateRating(Integer rating){
        return rating >= 1 && rating <=5;
    }
}
