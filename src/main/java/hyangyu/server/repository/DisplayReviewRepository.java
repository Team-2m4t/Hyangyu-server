package hyangyu.server.repository;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.DisplayReview;
import hyangyu.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DisplayReviewRepository extends JpaRepository<DisplayReview, Long> {

    @Query("select count(r) from DisplayReview r where r.display.displayId=?1 and r.user.userId=?2")
    int getCount(Long displayId, Long userId);

    @Query("select r from DisplayReview r where r.display.displayId=?1 and r.user.userId=?2")
    DisplayReview getDisplayReview(Long displayId, Long userId);

    List<DisplayReview> findAllByDisplayOrderByCreateTimeDesc(Display display);

    List<DisplayReview> findAllByUserOrderByReviewIdDesc(User user);
}
