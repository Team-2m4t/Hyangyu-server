package hyangyu.server.repository;

import hyangyu.server.domain.FairReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FairReviewRepository extends JpaRepository<FairReview, Long> {

    @Query("select count(r) from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    public int getCount(Long fairId, Long userId);

    @Query("select r from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    public FairReview getFairReview(Long fairId, Long userId);
}
