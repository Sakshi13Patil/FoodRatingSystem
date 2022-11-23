package org.foodRatingSystem.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    
    private String name;
    private String gender;
    private String phoneNumber;
    private String pincode;
}
