package sniffmap.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sniffmap.domain.User;
import sniffmap.ex.CustomValidationException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupFormDto signupFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("validation check failed.", errorMap);
        }
        User user = signupFormDto.toEntity();
        authService.signup(user);
        log.info(user.toString());
        return "auth/signin";
    }
}