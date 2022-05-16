package hyangyu.server.repository;

import hyangyu.server.domain.Fair;
import hyangyu.server.domain.FairReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FairReviewRepository extends JpaRepository<FairReview, Long> {

    @Query("select count(r) from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    int getCount(Long fairId, Long userId);

    @Query("select r from FairReview r where r.fair.fairId=?1 and r.user.userId=?2")
    FairReview getFairReview(Long fairId, Long userId);

    List<FairReview> findAllByFairOrderByCreateTimeDesc(Fair fair);
}
