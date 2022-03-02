package hyangyu.server.api;

import hyangyu.server.dto.ErrorDto;
import hyangyu.server.dto.UserDto;
import hyangyu.server.dto.event.*;
import hyangyu.server.repository.DisplayRepository;
import hyangyu.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryApi {

    private final UserService userService;
    private final DisplayRepository displayRepository;

    @GetMapping("/display/{order}/{page}")
    public ResponseEntity getCategory(@PathVariable("order") String order, @PathVariable("page") int page) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //order 파라미터 확인
        if (!order.equals("latest") && !order.equals("popularity") && !order.equals("charge") && !order.equals("free")) {
            return new ResponseEntity(new ErrorDto(404, "파라미터를 확인해주세요."), HttpStatus.BAD_REQUEST);
        }

        DisplayDto displays = displayRepository.getDisplay(user.getUserId(), order, page);

        DisplayResponseDto myPageResponseDto = new DisplayResponseDto(200, displays);
        return new ResponseEntity<>(myPageResponseDto, httpHeaders, HttpStatus.OK);
    }
}
