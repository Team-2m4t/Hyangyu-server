package hyangyu.server.repository;

import hyangyu.server.domain.Festival;
import hyangyu.server.domain.FestivalReview;
import hyangyu.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FestivalReviewRepository extends JpaRepository<FestivalReview, Long> {

    @Query("select count(r) from FestivalReview r where r.festival.festivalId=?1 and r.user.userId=?2")
    int getCount(Long festivalId, Long userId);

    @Query("select r from FestivalReview r where r.festival.festivalId=?1 and r.user.userId=?2")
    FestivalReview getFestivalReview(Long festivalId, Long userId);

    List<FestivalReview> findAllByFestivalOrderByCreateTimeDesc(Festival festival);

    List<FestivalReview> findAllByUserOrderByReviewIdDesc(User user);
}
