package hyangyu.server.repository;

import hyangyu.server.domain.FestivalReview;
import hyangyu.server.dto.review.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FestivalReviewRepository extends JpaRepository<FestivalReview, Long> {

    @Query("select count(r) from FestivalReview r where r.festival.festivalId=?1 and r.user.userId=?2")
    public int getCount(Long festivalId, Long userId);

    @Query("select r from FestivalReview r where r.festival.festivalId=?1 and r.user.userId=?2")
    public FestivalReview getFestivalReview(Long festivalId, Long userId);

    @Query("select new hyangyu.server.dto.review.ReviewDto(r.reviewId, r.user.image, r.user.username, r.createTime, r.content, r.score) from FestivalReview r where r.user.userId=?1  order by r.createTime desc")
    public List<ReviewDto> getMyFestivalReviews(Long userId);
}
