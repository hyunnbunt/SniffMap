package sniffmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sniffmap.entity.Parent;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByUsername(String username);
}
