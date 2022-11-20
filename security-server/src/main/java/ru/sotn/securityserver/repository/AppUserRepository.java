package ru.sotn.securityserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.securityserver.domain.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByEmail(String email);
}
