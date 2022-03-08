package hyangyu.server.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hyangyu.server.dto.EmailDto;
import hyangyu.server.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class EmailService {

	private final JavaMailSender javaMailSender;
	
	@Transactional
	public ResponseDto sendMail(EmailDto email) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			 	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
	            mimeMessageHelper.setTo(email.getTo()); // 메일 수신자
	            mimeMessageHelper.setSubject(email.getSubject()); // 메일 제목
	            mimeMessageHelper.setText(email.getMessage(), false); // 메일 본문 내용, HTML 여부
	            javaMailSender.send(mimeMessage);
	            return new ResponseDto(HttpStatus.OK.value(), "입력하신 이메일 주소로 숫자 코드를 전송하였습니다.");
	        } catch (MessagingException e) {
	            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "오류로 인해 숫자 코드를 전송하지 못하였습니다.");
	        }
	    }
}
