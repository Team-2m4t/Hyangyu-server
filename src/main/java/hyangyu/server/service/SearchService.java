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

    public SearchResponseDto getSearchData() {
        List<EventDto> allDisplay = displayRepository.getAllDisplay();
        List<EventDto> allFair = fairRepository.getAllFair();
        List<EventDto> allFestival = festivalRepository.getAllFestival();
        return new SearchResponseDto(allDisplay, allFair, allFestival);
    }
}
