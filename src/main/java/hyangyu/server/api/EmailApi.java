package hyangyu.server.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyangyu.server.domain.AuthNum;
import hyangyu.server.dto.EmailDto;
import hyangyu.server.dto.ResponseDto;
import hyangyu.server.service.AuthNumService;
import hyangyu.server.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailApi {

	private final EmailService emailService;
	private final AuthNumService authNumService;
	
	@PostMapping("/send-email/{email}")
	public ResponseDto sendMail(@PathVariable String email) {
		
		AuthNum authNum = authNumService.generateAuthNum(email);
		
		EmailDto emailMessage = EmailDto.builder()
				.to(authNum.getEmail())
				.subject("향유 비밀번호 변경 확인 이메일")
				.message("향유 비밀번호 변경을 위한 인증 번호는 다음과 같습니다: " +authNum.getAuthNum())
				.build();
		return emailService.sendMail(emailMessage);
	}
}
