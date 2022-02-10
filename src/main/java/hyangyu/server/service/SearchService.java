package hyangyu.server.service;

import hyangyu.server.dto.EventDto;
import hyangyu.server.dto.event.DisplayDto;
import hyangyu.server.dto.event.SearchResponseDto;
import hyangyu.server.repository.DisplayRepository;
import hyangyu.server.repository.FairRepository;
import hyangyu.server.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final DisplayRepository displayRepository;
    private final FairRepository fairRepository;
    private final FestivalRepository festivalRepository;

    public SearchResponseDto getSearchData(String keyword) {
        List<EventDto> allDisplay = displayRepository.findDisplays(keyword);
        List<EventDto> allFair = fairRepository.findFairs(keyword);
        List<EventDto> allFestival = festivalRepository.findFestivals(keyword);
        return new SearchResponseDto(allDisplay, allFair, allFestival);
    }
}
