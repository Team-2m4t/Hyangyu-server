package hyangyu.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyangyu.server.domain.*;
import hyangyu.server.dto.EventDto;
import hyangyu.server.dto.event.DisplayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DisplayRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QDisplay display = QDisplay.display;
    QFavoriteDisplay favoriteDisplay = QFavoriteDisplay.favoriteDisplay;

    public DisplayRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveDisplay(Display display) {
        em.persist(display);
    }

    public Optional<Display> findOne(Long displayId) {
        return Optional.ofNullable(em.find(Display.class, displayId));
    }

    public DisplayDto getDisplay(Long userId, String order, int page) {

        List<Display> displays = new ArrayList<>();

        if (order.equals("latest")) {
            displays = queryFactory.select(display)
                    .from(display)
                    .orderBy(display.startDate.desc())
                    .offset(page * 10)
                    .limit(10)
                    .fetch();

        } else if (order.equals("popularity")) {
            displays = queryFactory.select(display)
                    .from(display)
                    .orderBy(display.likey.desc())
                    .offset(page * 10)
                    .limit(10)
                    .fetch();
        } else if (order.equals("charge")) {
            displays = queryFactory.select(display)
                    .from(display)
                    .where(display.price.gt(0))
                    .offset(page * 10)
                    .limit(10)
                    .fetch();
        } else if (order.equals("free")) {
            displays = queryFactory.select(display)
                    .from(display)
                    .where(display.price.eq(0))
                    .offset(page * 10)
                    .limit(10)
                    .fetch();
        }

        List<EventDto> displayList = new ArrayList<>();
        for (Display display : displays) {
            EventDto eventDto = new EventDto(display);
            eventDto.setSaved(false);
            displayList.add(eventDto);
        }

        return new DisplayDto(displayList);
    }

    public List<EventDto> getAllDisplay() {
        List<Display> display = em.createQuery("select d from Display d", Display.class)
                .getResultList();
        List<EventDto> resultList = new ArrayList<>();
        for (Display d : display) {
            resultList.add(new EventDto(d));
        }

        return resultList;
    }
}
