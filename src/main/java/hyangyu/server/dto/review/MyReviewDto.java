package hyangyu.server.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MyReviewDto {

    Long reviewId;

    String eventTitle;

    String photo;

    String username;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    LocalDateTime createTime;

    String content;

    int score;
}
