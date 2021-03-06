package hyangyu.server.repository;

import hyangyu.server.domain.Festival;
import hyangyu.server.dto.event.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FestivalRepository {

    private final EntityManager em;

    public void saveFestival(Festival festival) {
        em.persist(festival);
    }

    public Optional<Festival> findOne(Long festivalId) {
        return Optional.ofNullable(em.find(Festival.class, festivalId));
    }

    public List<EventDto> findFestivals(String keyword) {
        List<Festival> festival = em.createQuery("select d from Festival d where d.title like CONCAT('%', :keyword, '%')", Festival.class)
                .setParameter("keyword", keyword)
                .getResultList();
        List<EventDto> resultList = new ArrayList<>();
        for (Festival d : festival) {
            resultList.add(new EventDto(d));
        }

        return resultList;
    }
}
