package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.FavoriteDisplay;
import hyangyu.server.domain.User;
import hyangyu.server.dto.event.DisplayDto;
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
    private final DisplayRepository displayRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Optional<FavoriteDisplay> saveFavoriteDisplay(Long userId, Long displayId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Display> display = displayRepository.findOne(displayId);
        FavoriteDisplay favoriteDisplay = new FavoriteDisplay(user.get(), display.get());
        Optional<FavoriteDisplay> result = Optional.ofNullable(favoriteDisplayRepository.findOne(favoriteDisplay.getFavoriteDisplayId()));
        if (result.isEmpty()) {
            favoriteDisplayRepository.saveFavoriteDisplay(favoriteDisplay);
        }
        return result;
    }

    @Transactional(readOnly = false)
    public Optional<FavoriteDisplay> deleteFavoriteDisplay(Long userId, Long displayId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Display> display = displayRepository.findOne(displayId);
        FavoriteDisplay favoriteDisplay = new FavoriteDisplay(user.get(), display.get());
        Optional<FavoriteDisplay> result = Optional.ofNullable(favoriteDisplayRepository.findOne(favoriteDisplay.getFavoriteDisplayId()));
        if (result.isPresent()) {
            favoriteDisplayRepository.deleteFavoriteDisplay(favoriteDisplay);
        }
        return result;
    }

    public DisplayDto getFavoriteDisplay(Long userId, int page) {
        DisplayDto myDisplayDto = favoriteDisplayRepository.getFavoriteDisplay(userId, page);
        return myDisplayDto;
    }


}
