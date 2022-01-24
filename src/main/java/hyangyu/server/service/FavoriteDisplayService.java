package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.FavoriteDisplay;
import hyangyu.server.domain.FavoriteDisplayId;
import hyangyu.server.domain.User;
import hyangyu.server.repository.DisplayRepository;
import hyangyu.server.repository.FavoriteDisplayRepository;
import hyangyu.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteDisplayService {

    private final FavoriteDisplayRepository favoriteDisplayRepository;
    private final UserRepository userRepository;
    private final DisplayRepository displayRepository;

    @Transactional(readOnly = false)
    public Optional<FavoriteDisplay> saveFavoriteDisplay(Long userId, Long displayId) throws Exception {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Display> display = displayRepository.findOne(displayId);
        FavoriteDisplay favoriteDisplay = new FavoriteDisplay(user.get(), display.get());
        Optional<FavoriteDisplay> result = Optional.ofNullable(favoriteDisplayRepository.findOne(favoriteDisplay.getFavoriteDisplayId()));
        if (result.isEmpty()) {
            favoriteDisplayRepository.saveFavoriteDisplay(favoriteDisplay);
        }
        return result;
    }
}
