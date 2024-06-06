package sniffmap.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sniffmap.domain.CustomUserDetails;
import sniffmap.domain.UserRepository;
import sniffmap.dto.ParentDto;
import sniffmap.jwt.JwtTokenProvider;
import sniffmap.service.ParentProfileService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ParentProfileService parentProfileService;

    @GetMapping("/login")
    public ResponseEntity<ParentDto> showParentProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            ParentDto parentDto = parentProfileService.showProfile(customUserDetails.getUsername());
            if (parentDto == null) {
                parentDto = parentProfileService.registerProfile(customUserDetails);
                log.info("New Owner entity created with id: " + parentDto.getNumber());
            } else {
                log.info("Already registered owner with id: " + parentDto.getUsername());
            }
            ParentDto savedParentDto = parentProfileService.registerProfile(customUserDetails);
            log.info(savedParentDto.toString());
            return ResponseEntity.status(HttpStatus.OK).body(savedParentDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/auth/signin")
    public ResponseEntity<?> login(@RequestBody SigninFormDto request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword(), Collections.emptyList());
            Authentication authentication = authenticationManager.authenticate(token);
            CustomUserDetails customUserDetailsDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetailsDetails.getUsername());
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", jwt);
            return ResponseEntity.status(HttpStatus.OK).body(tokenMap);
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            throw new RuntimeException("Invalid login credentials");
        }
    }
    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody SignupFormDto signupFormDto) {
        log.info("signup form requested: " + signupFormDto.getEmail());
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errorMap = new HashMap<>();
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            }
//            throw new CustomValidationException("validation check failed.", errorMap);
//        }
//        User user = signupFormDto.toEntity();
//        User savedUser = userDetailsService.registerUser(user);
//        return ResponseEntity.status(HttpStatus.OK).body("signup succeed, user id: " + user.getId());
//    }
//
//    // 사용자 이름 중복 확인
//        if (userRepository.existsByUsername(signupRequest.getUsername())) {
//        return ResponseEntity
//                .badRequest()
//                .body(new MessageResponse("Error: Username is already taken!"));
//    }

    // 사용자 객체 생성 및 비밀번호 암호화
    CustomUserDetails customUserDetails = new CustomUserDetails(signupFormDto.getEmail(),
            passwordEncoder.encode(signupFormDto.getPassword()), signupFormDto.getUsername());

    // 사용자 정보 저장
        CustomUserDetails savedCustomUserDetails = userRepository.save(customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body("signup succeed, user number: " + savedCustomUserDetails.getNumber() + "encoded password: " + savedCustomUserDetails.getPassword());
    }
}

