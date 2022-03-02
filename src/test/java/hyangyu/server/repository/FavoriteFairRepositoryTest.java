package hyangyu.server.repository;

import hyangyu.server.domain.*;
import hyangyu.server.dto.TestEventDto;
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
public class FavoriteFairRepositoryTest {
    @Autowired
    FavoriteFairRepository favoriteFairRepository;
    @Autowired
    FairRepository fairRepository;
    @Autowired
    EntityManager em;

    @Test
    void saveFair() {

        //given

        //사용자 생성
        User user = User.createUser("hyangyu@google.com", "abcd1234", "향유향유", null, "token", null);
        em.persist(user);

        //박람회 생성
        TestEventDto eventDto = new TestEventDto(Date.valueOf("2021-01-24"), Date.valueOf("2021-01-28"), "테스트 전시2", 5, 0, Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), "서울", "향유미술관", "매주 월요일", "세부내용", "", "", "", 0);
        Fair fair = Fair.createFair(eventDto);
        fairRepository.saveFair(fair);

        //내가 저장한 박람회 생성
        FavoriteFair favoriteFair = new FavoriteFair(user, fair);

        //when
        favoriteFairRepository.saveFavoriteFair(favoriteFair);

        //then
        Assertions.assertEquals(fair, fairRepository.findOne(fair.getFairId()).get());
        Assertions.assertEquals(fair, favoriteFairRepository.findOne(favoriteFair.getFavoriteFairId()).getFair());
    }
}
