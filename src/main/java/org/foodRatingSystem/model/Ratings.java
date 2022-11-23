package org.foodRatingSystem.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ratings {

    private int rating; //out of 5 allowed
    private String review;
    private String createdBy;
}
