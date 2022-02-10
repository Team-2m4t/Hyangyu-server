package hyangyu.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyReviewsResponseDto {
    int status;
    MyReviewsDto data;
}
