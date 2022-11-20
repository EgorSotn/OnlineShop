package ru.sotn.securityserver.service;

import ru.sotn.securityserver.domain.AppUser;
import ru.sotn.securityserver.domain.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveAppUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToAppUser(String email, String roleName);
    AppUser getAppUser(String email);
    List<AppUser> getAppUsers();
}
