package hyangyu.server.repository;

import hyangyu.server.domain.Festival;
import hyangyu.server.domain.FavoriteFestival;
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
public class FavoriteFestivalRepositoryTest {
    @Autowired
    FavoriteFestivalRepository favoriteFestivalRepository;
    @Autowired
    FestivalRepository festivalRepository;
    @Autowired
    EntityManager em;

    @Test
    void saveFestival() {

        //given

        //사용자 생성
        User user = User.createUser("hyangyu@google.com", "abcd1234", "향유향유", null, "token", null);
        em.persist(user);

        //박람회 생성
        TestEventDto eventDto = new TestEventDto(Date.valueOf("2021-01-24"), Date.valueOf("2021-01-28"), "테스트 전시2", 5, 0, Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), "서울", "향유미술관", "매주 월요일", "세부내용", "", "", "", 0);
        Festival festival = Festival.createFestival(eventDto);

        festivalRepository.saveFestival(festival);

        //내가 저장한 페스티벌 생성
        FavoriteFestival favoriteFestival = new FavoriteFestival(user, festival);

        //when
        favoriteFestivalRepository.saveFavoriteFestival(favoriteFestival);

        //then
        Assertions.assertEquals(festival, festivalRepository.findOne(festival.getFestivalId()).get());
        Assertions.assertEquals(festival, favoriteFestivalRepository.findOne(favoriteFestival.getFavoriteFestivalId()).getFestival());
    }
}
