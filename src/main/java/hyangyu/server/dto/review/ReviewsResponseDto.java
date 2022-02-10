package hyangyu.server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewsResponseDto {
    int status;
    ShowReviewsDto data;
}
