package sniffmap.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @NotBlank
    private String name;
    public User toEntity() {
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .name(name)
                .build();
    }
}