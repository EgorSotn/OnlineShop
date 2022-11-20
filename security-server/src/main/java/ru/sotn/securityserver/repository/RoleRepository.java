package ru.sotn.securityserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.securityserver.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findRoleByName(String name);
}
