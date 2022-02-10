package hyangyu.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyReviewsDto {

    List<ReviewDto> displays;
    List<ReviewDto> fairs;
    List<ReviewDto> festivals;
}
