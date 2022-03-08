package hyangyu.server.service;


import hyangyu.server.domain.Festival;
import hyangyu.server.domain.FavoriteFestival;
import hyangyu.server.domain.User;
import hyangyu.server.dto.event.FestivalDto;
import hyangyu.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteFestivalService {

    private final FestivalRepository festivalRepository;
    private final FavoriteFestivalRepository favoriteFestivalRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public Optional<FavoriteFestival> saveFavoriteFestival(Long userId, Long festivalId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Festival> festival = festivalRepository.findOne(festivalId);
        FavoriteFestival favoriteFestival = new FavoriteFestival(user.get(), festival.get());
        Optional<FavoriteFestival> result = Optional.ofNullable(favoriteFestivalRepository.findOne(favoriteFestival.getFavoriteFestivalId()));
        if (result.isEmpty()) {
            favoriteFestivalRepository.saveFavoriteFestival(favoriteFestival);
        }
        return result;
    }

    @Transactional(readOnly = false)
    public Optional<FavoriteFestival> deleteFavoriteFestival(Long userId, Long festivalId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Festival> festival = festivalRepository.findOne(festivalId);
        FavoriteFestival favoriteFestival = new FavoriteFestival(user.get(), festival.get());
        Optional<FavoriteFestival> result = Optional.ofNullable(favoriteFestivalRepository.findOne(favoriteFestival.getFavoriteFestivalId()));
        if (result.isPresent()) {
            favoriteFestivalRepository.deleteFavoriteFestival(favoriteFestival);
        }
        return result;
    }

    public FestivalDto getFavoriteFestival(Long userId, int page) {
        FestivalDto myFestivalDto = favoriteFestivalRepository.getFavoriteFestival(userId, page);
        return myFestivalDto;
    }
}
