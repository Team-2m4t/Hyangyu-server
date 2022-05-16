package hyangyu.server.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {

    Long reviewId;

    String image;

    String username;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    LocalDateTime createTime;

    String content;

    int score;

    private ReviewDto(Long reviewId, String image, String username, LocalDateTime createTime, String content, int score) {
        this.reviewId = reviewId;
        this.image = image;
        this.username = username;
        this.createTime = createTime;
        this.content = content;
        this.score = score;
    }

    public static ReviewDto of(Long reviewId, String image, String username, LocalDateTime createTime, String content, int score) {
        return new ReviewDto(reviewId, image, username, createTime, content, score);
    }
}
