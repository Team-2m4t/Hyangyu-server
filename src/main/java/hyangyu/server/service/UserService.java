package hyangyu.server.service;

import java.util.Collections;
import java.util.Optional;

import hyangyu.server.dto.ResponseDto;
import hyangyu.server.dto.TokenDto;
import hyangyu.server.dto.UserDto;
import hyangyu.server.domain.Authority;
import hyangyu.server.domain.User;
import hyangyu.server.jwt.DuplicateMemberException;
import hyangyu.server.repository.UserRepository;
import hyangyu.server.jwt.SecurityUtil;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        
        User user = User.builder()
        		.email(userDto.getEmail())
                .username(userDto.getUsername())
                .sub(userDto.getSub())
                .token(userDto.getToken())
                .image(userDto.getImage())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .build();

        return UserDto.from(userRepository.save(user));
    }
    
    @Transactional
    public String modifyUsername(UserDto userDto, String username) {
    	User userEntity = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() ->new IllegalArgumentException("해당 회원이 없습니다."));
    	userEntity.setUsername(username);
    	
    	return username;
    }
    
    @Transactional
    public String modifyPassword(String email, String password) {
    	User userEntity = userRepository.findByEmail(email).orElseThrow(() ->new IllegalArgumentException("해당 회원이 없습니다."));
    	if(password.length()<8 || password.length() >20) {
    		return "비밀번호는 8글자에서 20글자 사이어야 합니다.";
    	}
    	userEntity.setPassword(passwordEncoder.encode(password));
    	
    	return email;
    }
    
    @Transactional
    public ResponseDto deleteMyUser(UserDto userDto) {
    	User user = userRepository.findById(userDto.getUserId())
    			.orElseThrow(()->new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userDto.getUserId()));
    	userRepository.deleteById(userDto.getUserId());
    	return new ResponseDto(HttpStatus.OK.value(),"회원 탈퇴가 완료되었습니다");
    }
    
    @Transactional
    public ResponseDto saveMyUserToken(UserDto userDto, TokenDto token) {
    	User user = userRepository.findById(userDto.getUserId())
    			.orElseThrow(()->new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + userDto.getUserId()));
    	user.setToken(token.getToken());
    	return new ResponseDto(HttpStatus.OK.value(), userDto.getUserId()+" 푸시 토큰 저장 완료");
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String email) {
        return UserDto.from(userRepository.findByEmail(email).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentEmail().flatMap(userRepository::findByEmail).orElse(null));
    }
    
}
