package sniffmap.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sniffmap.domain.CustomUserDetails;
import sniffmap.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public CustomUserDetails signup(CustomUserDetails customUserDetails) {
        return userRepository.save(customUserDetails);
    }
    public CustomUserDetails getUser(String email) {return userRepository.findByEmail(email).orElseThrow();}
}