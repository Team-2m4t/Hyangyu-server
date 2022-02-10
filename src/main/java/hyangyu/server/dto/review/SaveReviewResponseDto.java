package hyangyu.server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveReviewResponseDto {
    int status;
    String message;
}
