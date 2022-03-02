package hyangyu.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyangyu.server.domain.Festival;
import hyangyu.server.domain.FavoriteFestival;
import hyangyu.server.domain.FavoriteFestivalId;
import hyangyu.server.domain.QFavoriteFestival;
import hyangyu.server.domain.QFestival;
import hyangyu.server.dto.event.EventDto;
import hyangyu.server.dto.event.FestivalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteFestivalRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QFestival festival = QFestival.festival;
    QFavoriteFestival favoriteFestival = QFavoriteFestival.favoriteFestival;

    public FavoriteFestivalRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveFavoriteFestival(FavoriteFestival favoriteFestival) {
        em.persist(favoriteFestival);
    }

    public FavoriteFestival findOne(FavoriteFestivalId favoriteFestivalId) {
        return em.find(FavoriteFestival.class, favoriteFestivalId);
    }

    public void deleteFavoriteFestival(FavoriteFestival favoriteFestival) { em.remove(em.find(FavoriteFestival.class, favoriteFestival.getFavoriteFestivalId()));}


    public FestivalDto getFavoriteFestival(Long userId, int page) {

        List<Festival> festivals = queryFactory.select(favoriteFestival.festival)
                .from(favoriteFestival)
                .where(favoriteFestival.user.userId.castToNum(Long.class).eq(userId))
                .orderBy(favoriteFestival.festival.endDate.asc())
                .offset(page*10)
                .limit(10)
                .fetch();

        List<EventDto> festivalList = new ArrayList<>();
        for (Festival festival : festivals) {
            EventDto eventDto = new EventDto(festival);
            eventDto.setSaved(true);
            festivalList.add(eventDto);
        }

        FestivalDto myFestival = new FestivalDto(festivalList);

        return myFestival;

    }
}
