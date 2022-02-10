package hyangyu.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hyangyu.server.domain.*;
import hyangyu.server.dto.EventDto;
import hyangyu.server.dto.event.DisplayDto;
import hyangyu.server.dto.event.FairDto;
import hyangyu.server.dto.event.FestivalDto;
import hyangyu.server.dto.MyPageDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MyPageRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    QFavoriteDisplay favoriteDisplay = QFavoriteDisplay.favoriteDisplay;
    QFavoriteFair favoriteFair = QFavoriteFair.favoriteFair;
    QFavoriteFestival favoriteFestival = QFavoriteFestival.favoriteFestival;

    public MyPageRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
    기획 발표회 이후에 수정
    public MyPageDto getMyPage(Long userId) {

        List<Display> displays = queryFactory.select(favoriteDisplay.display)
                .from(favoriteDisplay)
                .where(favoriteDisplay.user.userId.castToNum(Long.class).eq(userId))
                .orderBy(favoriteDisplay.display.endDate.asc())
                .limit(10)
                .fetch();

        List<EventDto> displayList = new ArrayList<>();
        for (Display display : displays) {
            EventDto eventDto = new EventDto(display);
            eventDto.setSaved(true);
            displayList.add(eventDto);
        }

        MyPageDto myPageDto = new MyPageDto(displayList);

        return myPageDto;

    }
     */

    public DisplayDto getMyDisplay(Long userId, int page) {

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

        DisplayDto myDisplay = new DisplayDto(displayList);

        return myDisplay;

    }

    public FairDto getMyFair(Long userId, int page) {

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

        FairDto myFair = new FairDto(fairList);

        return myFair;

    }

    public FestivalDto getMyFestival(Long userId, int page) {

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
