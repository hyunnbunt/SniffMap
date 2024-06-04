package sniffmap.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;

@Entity
@Table(name = "privileges")
@Getter
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    // getters and setters
}
