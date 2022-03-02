package hyangyu.server.repository;

import hyangyu.server.domain.Fair;
import hyangyu.server.dto.event.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FairRepository {
    private final EntityManager em;

    public void saveFair(Fair fair) {
        em.persist(fair);
    }

    public Optional<Fair> findOne(Long fairId) {
        return Optional.ofNullable(em.find(Fair.class, fairId));
    }

    public List<EventDto> findFairs(String keyword) {
        List<Fair> fair = em.createQuery("select d from Fair d where d.title like CONCAT('%', :keyword, '%')", Fair.class)
                .setParameter("keyword", keyword)
                .getResultList();
        List<EventDto> resultList = new ArrayList<>();
        for (Fair d : fair) {
            resultList.add(new EventDto(d));
        }

        return resultList;
    }
}
