package hyangyu.server.dto.review;

import lombok.Getter;

import java.util.List;

@Getter
public class MyReviewResponseDto {
    List<ReviewDto> display;
    List<ReviewDto> fair;
    List<ReviewDto> festival;

    private MyReviewResponseDto(List<ReviewDto> display, List<ReviewDto> fair, List<ReviewDto> festival) {
        this.display = display;
        this.fair = fair;
        this.festival = festival;
    }

    public static MyReviewResponseDto of(List<ReviewDto> display, List<ReviewDto> fair, List<ReviewDto> festival) {
        return new MyReviewResponseDto(display, fair, festival);
    }
}
