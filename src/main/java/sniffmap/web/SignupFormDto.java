package sniffmap.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sniffmap.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SignupFormDto {
    @Size(max = 10)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}