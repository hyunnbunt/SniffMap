package sniffmap.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class CustomUserDetails implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
    @Column(length = 10, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)  //@Column은 기본적으로 nullable이 true
    private String password;
    @Column(nullable = false)
    private Collection<GrantedAuthority> authorities;	//권한 목록
    @Column(nullable = false)
    private boolean accountNonExpired;
    @Column(nullable = false)
    private boolean accountNonLocked;
    @Column(nullable = false)
    private boolean credentialsNonExpired;
    @Column(nullable = false)
    private boolean enabled;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_number"),
//            inverseJoinColumns = @JoinColumn(name = "role_number")
//    )
//    private Collection<Role> roles;

    public CustomUserDetails(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.authorities = Collections.emptyList();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
//        log.info("" + roles.stream()
//                .map(role ->
//                        new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList()).size());
//        return roles.stream()
//                .map(role ->
//                    new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
    }
    //
//    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
//        return roles.stream()
//                .flatMap(role -> role.getPrivileges().stream())
//                .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
//                .collect(Collectors.toList());
//    }

//    public User(User user, Collection<GrantedAuthority> authorities) {
//        this.user = user;
//        this.authorities = authorities;
////        this.accountNonExpired = true;
////        this.accountNonLocked = true;
////        this.credentialsNonExpired = true;
////        this.enabled = true;
//    }

    /**
     * 해당 유저의 권한 목록
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList();
//    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return this.password;
    }


    /**
     * PK값
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        //이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return true;
    }

}