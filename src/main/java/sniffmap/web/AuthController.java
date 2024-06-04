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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sniffmap.domain.User;
import sniffmap.domain.UserService;
import sniffmap.ex.CustomValidationException;
import sniffmap.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService userDetailsService;
    @PostMapping("/auth/signin")
    public String login(@RequestBody SigninFormDto request) {
        log.info(request.getEmail());
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            log.info(authentication.isAuthenticated() + "authentication check");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenProvider.generateToken(userDetails.getUsername());
        } catch (AuthenticationException e) {
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
        User user = signupFormDto.toEntity();
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("signup succeed, user id: " + user.getId());
    }
}

