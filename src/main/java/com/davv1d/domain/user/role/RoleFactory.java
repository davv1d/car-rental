package com.davv1d.domain.user.role;

public class RoleFactory {
    public static Role createRole(String roleName) {
        if (roleName.equalsIgnoreCase("admin")) {
            return Role.ROLE_ADMIN;
        } else if (roleName.equalsIgnoreCase("client")) {
            return Role.ROLE_CLIENT;
        } else {
            return Role.INCORRECT;
        }
    }
}
