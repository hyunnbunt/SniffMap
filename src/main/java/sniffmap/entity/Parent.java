package sniffmap.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import sniffmap.domain.CustomUserDetails;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long number;
    @Column(nullable = false, unique = true) // @Column 어노테이션이 없으면 이 컬럼 옵션이 nullable = false가 됨
    String username;
    @Column(nullable = false)
    String email;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Dog> dogs;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organizer", cascade = CascadeType.REMOVE)
    Set<Event> organizingEvents;
    Long ownerPoints;

//    public Parent(String email, String username) {
//        this.email = email;
//        this.username = username;
//    }

    public static Parent fromUserDetails(CustomUserDetails customUserDetails) {
        return Parent.builder()
                .username(customUserDetails.getUsername())
                .email(customUserDetails.getEmail()).build();
    }
}
