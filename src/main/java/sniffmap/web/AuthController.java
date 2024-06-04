package sniffmap.web;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

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