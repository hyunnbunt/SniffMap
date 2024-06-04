package sniffmap.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//    public User login(String email, String password) throws UsernameNotFoundException, BadCredentialsException, Throwable {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> {
//                    throw new UsernameNotFoundException("등록되지 않은 아이디입니다.");
//                });
//        if(!passwordEncoder.matches(password,user.getPassword())){
//            throw new BadCredentialsException("잘못된 비밀번호입니다.");
//        }
//        return user;
//    }
}
