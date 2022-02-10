package hyangyu.server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShowReviewsDto {
    List<ReviewDto> reviews;
}
