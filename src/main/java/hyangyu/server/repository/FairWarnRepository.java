package hyangyu.server.repository;

import hyangyu.server.domain.FairWarn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FairWarnRepository extends JpaRepository<FairWarn, Long> {

    @Query("select w from FairWarn w where w.fairReview.reviewId=?1 and w.user.userId=?2")
    Optional<FairWarn> getFairWarn(Long reviewId, Long userId);

    @Modifying
    @Transactional
    @Query("delete from FairWarn w where w.fairReview.reviewId=?1")
    void deleteFairWarns(Long reviewId);
}
