package hyangyu.server.repository;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.FavoriteDisplay;
import hyangyu.server.domain.User;
import hyangyu.server.dto.event.TestEventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.sql.Time;


@SpringBootTest
@Transactional
public class FavoriteDisplayRepositoryTest {

    @Autowired
    FavoriteDisplayRepository favoriteDisplayRepository;
    @Autowired
    DisplayRepository displayRepository;
    @Autowired
    EntityManager em;

    @Test
    void saveDisplay() {

        //given

        //사용자 생성
        User user = User.createUser("hyangyu@naver.com", "abcd1234", "향유", null, "token", null);
        em.persist(user);

        //전시 생성
        TestEventDto eventDto = new TestEventDto(Date.valueOf("2021-01-16"), Date.valueOf("2021-01-26"), "테스트 전시", 3, 0, Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), "서울", "세종미술관", "매주 수요일", "세부내용", "", "", "", 0);
        Display display = Display.createDisplay(eventDto);

        displayRepository.saveDisplay(display);

        //내가 저장한 전시회 생성
        FavoriteDisplay favoriteDisplay = new FavoriteDisplay(user, display);

        //when
        favoriteDisplayRepository.saveFavoriteDisplay(favoriteDisplay);

        //then
        Assertions.assertEquals(display, displayRepository.findOne(display.getDisplayId()).get());
        Assertions.assertEquals(display, favoriteDisplayRepository.findOne(favoriteDisplay.getFavoriteDisplayId()).getDisplay());
    }



}
