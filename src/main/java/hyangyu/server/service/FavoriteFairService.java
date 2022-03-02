package hyangyu.server.service;

import hyangyu.server.domain.*;
import hyangyu.server.dto.event.FairDto;
import hyangyu.server.repository.FairRepository;
import hyangyu.server.repository.FavoriteFairRepository;
import hyangyu.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteFairService {
    private final FavoriteFairRepository favoriteFairRepository;
    private final FairRepository fairRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = false)
    public Optional<FavoriteFair> saveFavoriteFair(Long userId, Long fairId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Fair> fair = fairRepository.findOne(fairId);
        FavoriteFair favoriteFair = new FavoriteFair(user.get(), fair.get());
        Optional<FavoriteFair> result = Optional.ofNullable(favoriteFairRepository.findOne(favoriteFair.getFavoriteFairId()));
        if (result.isEmpty()) {
            favoriteFairRepository.saveFavoriteFair(favoriteFair);
        }
        return result;
    }

    @Transactional(readOnly = false)
    public Optional<FavoriteFair> deleteFavoriteFair(Long userId, Long fairId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Fair> fair = fairRepository.findOne(fairId);
        FavoriteFair favoriteFair = new FavoriteFair(user.get(), fair.get());
        Optional<FavoriteFair> result = Optional.ofNullable(favoriteFairRepository.findOne(favoriteFair.getFavoriteFairId()));
        if (result.isPresent()) {
            favoriteFairRepository.deleteFavoriteFair(favoriteFair);
        }
        return result;
    }

    public FairDto getFavoriteFair(Long userId, int page) {
        FairDto myFairDto = favoriteFairRepository.getFavoriteFair(userId, page);
        return myFairDto;
    }

}
