package org.foodRatingSystem.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Restaurant {
    private String name;
    private List<String> areas;
    private String specialDish;
    private Double dishPrice;
    private List<Ratings> ratings;
    private double avgRating;
    private String createdBy;
}
