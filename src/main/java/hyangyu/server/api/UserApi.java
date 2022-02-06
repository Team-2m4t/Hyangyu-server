package hyangyu.server.api;

import hyangyu.server.dto.ModificationDto;
import hyangyu.server.dto.ResponseDto;
import hyangyu.server.dto.TokenDto;
import hyangyu.server.dto.UserDto;
import hyangyu.server.domain.User;
import hyangyu.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/UserApi")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/UserController/user");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(email));
    }
    
    @PostMapping("/user/modifyUsername")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> modifyUsername(HttpServletRequest request, @RequestBody ModificationDto user){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	String modifiedUsername = user.getUsername();
    	return ResponseEntity.ok(userService.modifyUsername(userDto, modifiedUsername));
    }
    
    @PostMapping("/user/modifyPassword")
    public ResponseEntity<String> modifyPassword(@RequestBody ModificationDto user){
    	return ResponseEntity.ok(userService.modifyPassword(user.getEmail(), user.getPassword()));
    }
    
    @DeleteMapping("/user/deleteMyUser")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ResponseDto> deleteMyUser(HttpServletRequest request){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	return ResponseEntity.ok(userService.deleteMyUser(userDto));
    }
    
    @PostMapping("/user/saveToken")
    public ResponseEntity<ResponseDto> saveMyUserToken(HttpServletRequest request, @RequestBody TokenDto token){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	return ResponseEntity.ok(userService.saveMyUserToken(userDto, token));
    }
}
