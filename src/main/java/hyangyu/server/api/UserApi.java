package hyangyu.server.api;

import hyangyu.server.dto.user.ModificationDto;
import hyangyu.server.dto.ResponseDto;
import hyangyu.server.dto.user.UserDto;
import hyangyu.server.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import hyangy.server.aws.S3Uploader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;
    private final S3Uploader s3Uploader;


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

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(email));
    }
    
    @PostMapping("/username")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ResponseDto> modifyUsername(HttpServletRequest request, @RequestBody ModificationDto user){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	String modifiedUsername = user.getUsername();
    	return ResponseEntity.ok(userService.modifyUsername(userDto, modifiedUsername));
    }
    
    @PostMapping("/image")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ResponseDto> uploadProfileImage(HttpServletRequest request, @RequestParam("images")MultipartFile multipartFile) throws IOException {
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	String imgurl = s3Uploader.upload(multipartFile, "static");
    	return ResponseEntity.ok(userService.modifyImg(userDto.getEmail(), imgurl));
    }
    
    @GetMapping("/image")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ResponseDto> getProfileImage(HttpServletRequest request){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	String imgPath = s3Uploader.getThumbnailPath(userDto.getImage());
    	return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), imgPath));
    }
  
    
    @PostMapping("/password")
    public ResponseEntity<ResponseDto> modifyPassword(@RequestBody ModificationDto user){
    	return ResponseEntity.ok(userService.modifyPassword(user.getEmail(), user.getPassword()));
    }
    
    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ResponseDto> deleteMyUser(HttpServletRequest request){
    	UserDto userDto = userService.getMyUserWithAuthorities();
    	return ResponseEntity.ok(userService.deleteMyUser(userDto));
    }
}