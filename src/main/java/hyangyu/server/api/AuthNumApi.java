package hyangyu.server.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyangyu.server.domain.AuthNum;
import hyangyu.server.dto.ResponseDto;
import hyangyu.server.repository.AuthNumRepository;
import hyangyu.server.service.AuthNumService;
import hyangyu.server.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthNumApi {

	private final AuthNumRepository authNumRepository;
	private final AuthNumService authNumService;
	
	@DeleteMapping("/authnum")
	public ResponseDto checkAuthNum(@Valid @RequestBody AuthNum authNum) {
		Boolean isSame = false;
		AuthNum realAuthNum = authNumRepository.findByEmail(authNum.getEmail()).orElseThrow(() ->new IllegalArgumentException("인증번호를 다시 발급해주세요."));
		
		if(realAuthNum.getAuthNum().equals(authNum.getAuthNum()))
			isSame = true;
		
		authNumService.deleteAuthNum(realAuthNum);
		
		if(isSame)
			return new ResponseDto(HttpStatus.OK.value(),"인증이 완료되었습니다.");
		else
			return new ResponseDto(HttpStatus.BAD_REQUEST.value(),"인증 번호가 일치하지 않습니다. 다시 인증해주세요.");
	}
}
