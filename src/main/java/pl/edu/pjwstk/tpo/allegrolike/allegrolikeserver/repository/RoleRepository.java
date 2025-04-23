package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.model.ERole;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
} 