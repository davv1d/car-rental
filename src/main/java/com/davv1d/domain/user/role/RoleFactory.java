package com.davv1d.domain.user.role;

import static com.davv1d.domain.user.role.Role.*;

public class RoleFactory {
    public static String createRole(String roleName) {
        if (roleName.equalsIgnoreCase("admin") || roleName.equals(ROLE_ADMIN.name())) {
            return ROLE_ADMIN.name();
        } else if (roleName.equalsIgnoreCase("client") || roleName.equals(ROLE_CLIENT.name())) {
            return ROLE_CLIENT.name();
        } else {
            return INCORRECT.name();
        }
    }
}
