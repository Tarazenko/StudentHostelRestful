package by.bntu.tarazenko.hostelrestful.repository;

import by.bntu.tarazenko.hostelrestful.models.ERole;
import by.bntu.tarazenko.hostelrestful.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
