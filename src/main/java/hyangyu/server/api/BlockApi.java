package hyangyu.server.api;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyangyu.server.domain.BlockedUser;
import hyangyu.server.domain.User;
import hyangyu.server.dto.ErrorDto;
import hyangyu.server.dto.ResponseDto;
import hyangyu.server.dto.UserDto;
import hyangyu.server.repository.BlockedUserRepository;
import hyangyu.server.repository.UserRepository;
import hyangyu.server.service.BlockService;
import hyangyu.server.service.DisplayService;
import hyangyu.server.service.FairService;
import hyangyu.server.service.FavoriteDisplayService;
import hyangyu.server.service.FavoriteFairService;
import hyangyu.server.service.FavoriteFestivalService;
import hyangyu.server.service.FestivalService;
import hyangyu.server.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class BlockApi {
	private final UserRepository userRepository;
	private final UserService userService;
	private final BlockService blockService;
	private final BlockedUserRepository blockedUserRepository;
    @PostMapping("/block/{reportedId}")
    public ResponseEntity saveBlock(@PathVariable Long reportedId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //차단할 유저 검색
        Optional<User> reportedUser = userRepository.findByUserId(reportedId);
        if(reportedUser.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "차단하려는 유저의 id가 잘못된 유저 id입니다."), HttpStatus.BAD_REQUEST);
        }

        //본인 유저 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if(user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //차단한 유저 검색
        Optional<BlockedUser> blockedUser = blockService.saveBlock(user.getUserId(), reportedId);
        if(blockedUser.isPresent()) {
            return new ResponseEntity(new ErrorDto(404, "이미 차단한 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        ResponseDto responseDto = new ResponseDto(200, "해당 사용자를 차단하였습니다.");
        return new ResponseEntity<>(responseDto, httpHeaders, HttpStatus.OK);
    }
    
    @DeleteMapping("/block/{reportedId}")
    public ResponseEntity deleteBlock(@PathVariable Long reportedId) throws Exception {
    	 HttpHeaders httpHeaders = new HttpHeaders();
    	 
    	 //차단해제할 유저 검색
         Optional<User> reportedUser = userRepository.findByUserId(reportedId);
         if(reportedUser.isEmpty()) {
             return new ResponseEntity(new ErrorDto(404, "차단 해제하려는 유저의 id가 잘못된 유저 id입니다."), HttpStatus.BAD_REQUEST);
         }
    	//본인 유저 검색
         UserDto user = userService.getMyUserWithAuthorities();
         if(user == null) {
             return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
         }
    	//차단한 유저 검색
         Optional<BlockedUser> blockedUser = blockService.deleteBlock(user.getUserId(), reportedId);
         if(!blockedUser.isPresent()) {
             return new ResponseEntity(new ErrorDto(404, "차단한 적 없는 사용자입니다."), HttpStatus.BAD_REQUEST);
         }
   	 
        ResponseDto responseDto = new ResponseDto(200, "해당 사용자를 차단 해제하였습니다.");
        return new ResponseEntity<>(responseDto, httpHeaders, HttpStatus.OK);

    }
    
}
