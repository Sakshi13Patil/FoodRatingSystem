package org.foodRatingSystem.context;

import lombok.Builder;
import lombok.Data;
import org.foodRatingSystem.model.User;

@Data
@Builder
public class RequestContext {
    private User loggedInUser;
}
