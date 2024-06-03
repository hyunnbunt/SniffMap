package sniffmap.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sniffmap.domain.User;
import sniffmap.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User signup(User user) {
        return userRepository.save(user);
    }
    public User getUser(String username) {return userRepository.findByUsername(username);}
}