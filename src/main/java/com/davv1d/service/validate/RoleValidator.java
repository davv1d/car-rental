package com.davv1d.service.validate;

import com.davv1d.domain.user.role.Role;
import com.davv1d.functional.Result;

import java.util.Arrays;

public class RoleValidator {
    public static final String ERROR = "INCORRECT ROLE NAME";

    public static Result<String> test(String role) {
        if (isRoleExist(role)) {
            return Result.success(role);
        } else {
            return Result.failure(ERROR);
        }
    }


    public static boolean isRoleExist(String userRole) {
        return Arrays.stream(Role.values())
                .map(role -> role.name().replace("ROLE_", ""))
                .anyMatch(roleName -> roleName.equalsIgnoreCase(userRole));
    }
}
