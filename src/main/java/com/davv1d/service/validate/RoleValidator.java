package com.davv1d.service.validate;

import com.davv1d.domain.user.role.Role;

import java.util.Arrays;

public class RoleValidator {
    public static boolean isRoleExist(String userRole) {
        return Arrays.stream(Role.values())
                .map(role -> role.name().replace("ROLE_", ""))
                .anyMatch(roleName -> roleName.equalsIgnoreCase(userRole));
    }
}
