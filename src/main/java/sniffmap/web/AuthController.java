package sniffmap.web;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sniffmap.domain.User;
import sniffmap.domain.User;
import sniffmap.domain.UserRepository;
import sniffmap.ex.CustomValidationException;
import sniffmap.jwt.JwtTokenProvider;

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
    @PostMapping("/auth/signin")
    public ResponseEntity<?> login(@RequestBody SigninFormDto request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword(), Collections.emptyList());
            Authentication authentication = authenticationManager.authenticate(token);
            User userDetails = (User) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(userDetails.getUsername());
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
    User user = new User(signupFormDto.getEmail(),
            passwordEncoder.encode(signupFormDto.getPassword()), signupFormDto.getUsername());

    // 사용자 정보 저장
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("signup succeed, user id: " + savedUser.getId() + "encoded password: " + savedUser.getPassword());
    }
}

