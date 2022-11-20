package ru.sotn.securityserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sotn.securityserver.controller.dto.RoleToUserForm;
import ru.sotn.securityserver.domain.AppUser;
import ru.sotn.securityserver.domain.Role;
import ru.sotn.securityserver.service.AppUserService;

import java.util.List;

@AllArgsConstructor
@RestController("/api")
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/user")
    public ResponseEntity<List<AppUser>> getAllUser(){
        return new ResponseEntity<>( appUserService.getAppUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<AppUser> getUserByEmail(@PathVariable(name = "email") String email){
        return new ResponseEntity<>(appUserService.getAppUser(email), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser){
        return new ResponseEntity<>(appUserService.saveAppUser(appUser), HttpStatus.OK);
    }
    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return new ResponseEntity<>(appUserService.saveRole(role), HttpStatus.OK);
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToAppUser(@RequestBody RoleToUserForm form){
        appUserService.addRoleToAppUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }


}
