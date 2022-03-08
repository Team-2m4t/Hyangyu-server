package hyangyu.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyangyu.server.domain.*;
import hyangyu.server.dto.event.DisplayDto;
import hyangyu.server.dto.event.EventDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteDisplayRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QFavoriteDisplay favoriteDisplay = QFavoriteDisplay.favoriteDisplay;

    public FavoriteDisplayRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveFavoriteDisplay(FavoriteDisplay favoriteDisplay) {
        em.persist(favoriteDisplay);
    }

    public FavoriteDisplay findOne(FavoriteDisplayId favoriteDisplayId) {
        return em.find(FavoriteDisplay.class, favoriteDisplayId);
    }

    public void deleteFavoriteDisplay(FavoriteDisplay favoriteDisplay) { em.remove(em.find(FavoriteDisplay.class, favoriteDisplay.getFavoriteDisplayId()));}

    public DisplayDto getFavoriteDisplay(Long userId, int page) {

        List<Display> displays = queryFactory.select(favoriteDisplay.display)
                .from(favoriteDisplay)
                .where(favoriteDisplay.user.userId.castToNum(Long.class).eq(userId))
                .orderBy(favoriteDisplay.display.endDate.asc())
                .offset(page*10)
                .limit(10)
                .fetch();

        List<EventDto> displayList = new ArrayList<>();
        for (Display display : displays) {
            EventDto eventDto = new EventDto(display);
            eventDto.setSaved(true);
            displayList.add(eventDto);
        }

        return new DisplayDto(displayList);


    }

}
