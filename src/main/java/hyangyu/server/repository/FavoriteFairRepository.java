package hyangyu.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyangyu.server.domain.Fair;
import hyangyu.server.domain.FavoriteFair;
import hyangyu.server.domain.FavoriteFairId;
import hyangyu.server.domain.QFair;
import hyangyu.server.domain.QFavoriteFair;
import hyangyu.server.dto.event.EventDto;
import hyangyu.server.dto.event.FairDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteFairRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QFavoriteFair favoriteFair = QFavoriteFair.favoriteFair;

    public FavoriteFairRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveFavoriteFair(FavoriteFair favoriteFair) {
        em.persist(favoriteFair);
    }

    public FavoriteFair findOne(FavoriteFairId favoriteFairId) {
        return em.find(FavoriteFair.class, favoriteFairId);
    }

    public void deleteFavoriteFair(FavoriteFair favoriteFair) { em.remove(em.find(FavoriteFair.class, favoriteFair.getFavoriteFairId()));}

    public FairDto getFavoriteFair(Long userId, int page) {

        List<Fair> fairs = queryFactory.select(favoriteFair.fair)
                .from(favoriteFair)
                .where(favoriteFair.user.userId.castToNum(Long.class).eq(userId))
                .orderBy(favoriteFair.fair.endDate.asc())
                .offset(page*10)
                .limit(10)
                .fetch();

        List<EventDto> fairList = new ArrayList<>();
        for (Fair fair : fairs) {
            EventDto eventDto = new EventDto(fair);
            eventDto.setSaved(true);
            fairList.add(eventDto);
        }

        return new FairDto(fairList);

    }
}
