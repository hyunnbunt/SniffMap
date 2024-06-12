package sniffmap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import sniffmap.jwt.JwtTokenFilter;
import sniffmap.jwt.JwtTokenProvider;
import sniffmap.web.CustomUserDetailsService;

import java.util.Collections;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //helloooo
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManager.class);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

//
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(customUserDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
//        customAuthenticationFilter.setFilterProcessesUrl("/auth/signin");
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://ec2-43-203-243-231.ap-northeast-2.compute.amazonaws.com/"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L);
            return config;
        }));

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, customUserDetailsService);
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers("/", "/auth/signup", "/auth/login").permitAll()
                        .anyRequest().authenticated()
        ).addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

//        http.formLogin(form -> form
//                .loginPage("http://localhost:5173/") // GET
                /* When the login page is specified in the Spring Security configuration,
                you are responsible for rendering the page. */
//                .loginProcessingUrl("/auth/signin") // POST
                    /* Spring Security will take care of the POST request to this url(for login). */
//                .defaultSuccessUrl("http://localhost:5173/")
//                    /* when the login is successed, go to this url. I should return the index page with this path.*/
//                .permitAll()
//        );

        return http.build();
    }

}