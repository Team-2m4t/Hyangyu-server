package hyangyu.server.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hyangyu.server.domain.AuthNum;
import hyangyu.server.jwt.DuplicateMemberException;
import hyangyu.server.repository.AuthNumRepository;
import lombok.RequiredArgsConstructor;
import java.util.Random;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Component
public class AuthNumService {

	private final AuthNumRepository authNumRepository;
	
	@Transactional
	public AuthNum generateAuthNum(String email) {
		
		if (authNumRepository.findByEmail(email).orElse(null) != null) {
            throw new DuplicateMemberException("인증번호 중복 생성 시도");
        }
		
		Random rnd =new Random();
		StringBuffer buf =new StringBuffer();

		for(int i=0;i<6;i++){
		    if(rnd.nextBoolean()){
		        buf.append((char)((int)(rnd.nextInt(26))+97));
		    }else{
		        buf.append((rnd.nextInt(10))); 
		    }
		}
		
		String randAuth = buf.toString();
		
		AuthNum authNum = AuthNum.builder()
				.email(email)
				.authNum(randAuth)
				.build();
		
		authNumRepository.save(authNum);
		
		return authNum;
	}
	
	@Transactional
	public void deleteAuthNum(AuthNum authNum) {
		AuthNum authNumEntity = authNumRepository.findByEmail(authNum.getEmail())
    			.orElseThrow(()->new IllegalArgumentException("해당 회원은 비밀번호 찾기를 시도한 적이 없습니다."));
    	authNumRepository.deleteById(authNumEntity.getEmail());
	}
}
