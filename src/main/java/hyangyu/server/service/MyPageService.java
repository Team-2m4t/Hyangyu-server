package hyangyu.server.service;

import hyangyu.server.dto.event.DisplayDto;
import hyangyu.server.dto.event.FairDto;
import hyangyu.server.dto.event.FestivalDto;
import hyangyu.server.dto.MyPageDto;
import hyangyu.server.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MyPageRepository myPageRepository;

    /*
    기획 발표회 이후에 수정
    public MyPageDto getMyPage(Long userId) {
        MyPageDto myPageDto = myPageRepository.getMyPage(userId);
        return myPageDto;
    }
     */

    public DisplayDto getMyDisplay(Long userId, int page) {
        DisplayDto myDisplayDto = myPageRepository.getMyDisplay(userId, page);
        return myDisplayDto;
    }

    public FairDto getMyFair(Long userId, int page) {
        FairDto myFairDto = myPageRepository.getMyFair(userId, page);
        return myFairDto;
    }

    public FestivalDto getMyFestival(Long userId, int page) {
        FestivalDto myFestivalDto = myPageRepository.getMyFestival(userId, page);
        return myFestivalDto;
    }
}
