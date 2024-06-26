package sniffmap.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import sniffmap.domain.Role;
import sniffmap.domain.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        return CustomUserDetails.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .username(user.getUsername())
//                .locked(false) // 임의의 값, User 엔티티 수정 후 값 전달 예정
//                .emailVerified(true)
//                .authorities(user.getAuthorities())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername, username: " + username);
        UserDetails user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));
        log.info(user.toString());
        return user;
    }

}
