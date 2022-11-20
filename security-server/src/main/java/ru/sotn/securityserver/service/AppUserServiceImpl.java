package ru.sotn.securityserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sotn.securityserver.domain.AppUser;
import ru.sotn.securityserver.domain.Role;
import ru.sotn.securityserver.exception.NotFoundException;
import ru.sotn.securityserver.repository.AppUserRepository;
import ru.sotn.securityserver.repository.RoleRepository;

import java.util.List;


@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
//    Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);
    @Override
    public AppUser saveAppUser(AppUser appUser) {
//        log.info("Saving new user {}", appUser.getEmail());
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
//        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAppUser(String email, String roleName) {
//        log.info("Adding role {} to user {}", roleName,email);
        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(()->new NotFoundException(email));
        Role role  = roleRepository.findRoleByName(roleName).orElseThrow(()->new NotFoundException(roleName));
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getAppUser(String email) {
//        log.info("Getting user by email {} ", email);
        return appUserRepository.findAppUserByEmail(email).orElseThrow(()->new NotFoundException(email));
    }

    @Override
    public List<AppUser> getAppUsers() {
//        log.info("find users");
        return appUserRepository.findAll();
    }

}
