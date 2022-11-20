package ru.sotn.securityserver.controller.dto;

import lombok.Data;

@Data
public class RoleToUserForm {
    private String email;
    private String roleName;
}
