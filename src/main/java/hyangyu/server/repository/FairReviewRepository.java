package hyangyu.server.repository;

import hyangyu.server.domain.FairReview;
import hyangyu.server.dto.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FairReviewRepository extends JpaRepository<FairReview, Long> {

    @Query("select count(r) from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    public int getCount(Long fairId, Long userId);

    @Query("select r from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    public FairReview getFairReview(Long fairId, Long userId);

    @Query("select new hyangyu.server.dto.ReviewDto(r.user.image, r.user.username, r.createTime, r.content, r.score) from FairReview r where r.user.userId=?1  order by r.createTime desc")
    public List<ReviewDto> getMyFairReviews(Long userId);
}
