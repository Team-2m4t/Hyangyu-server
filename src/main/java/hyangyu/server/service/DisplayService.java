package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.repository.DisplayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisplayService {

    private final DisplayRepository displayRepository;

    @Transactional(readOnly = false)
    public void saveDisplay(Display display) {
        displayRepository.saveDisplay(display);
    }

    public Optional<Display> findOne(Long displayId) throws Exception {
        Optional<Display> result = displayRepository.findOne(displayId);
        return result;
    }

}
