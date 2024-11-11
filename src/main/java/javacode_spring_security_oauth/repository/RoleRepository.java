package javacode_spring_security_oauth.repository;

import javacode_spring_security_oauth.model.ERole;
import javacode_spring_security_oauth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
