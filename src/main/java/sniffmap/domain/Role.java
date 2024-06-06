//package sniffmap.domain;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.ToString;
//
//import java.util.Collection;
//
//@Entity
//@Table(name = "roles")
//@Getter
//@ToString
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "privilege_id")
//    )
//    private Collection<Privilege> privileges;
//
//    // getters and setters
//}
//
