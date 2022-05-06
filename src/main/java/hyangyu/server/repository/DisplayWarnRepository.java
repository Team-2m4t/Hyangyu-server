package hyangyu.server.repository;

import hyangyu.server.domain.DisplayWarn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DisplayWarnRepository extends JpaRepository<DisplayWarn, Long> {

    @Query("select w from DisplayWarn w where w.displayReview.reviewId=?1 and w.user.userId=?2")
    Optional<DisplayWarn> getDisplayWarn(Long reviewId, Long userId);

    @Modifying
    @Transactional
    @Query("delete from DisplayWarn w where w.displayReview.reviewId=?1")
    void deleteDisplayWarns(Long reviewId);
}
