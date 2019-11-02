package com.davv1d.service.validate;

import com.davv1d.domain.user.login.SingUpDto;
import com.davv1d.domain.user.role.Role;
import com.davv1d.functional.Result;

import java.util.Arrays;

public class RoleValidator {

    public static Result<SingUpDto> roleValidator(final SingUpDto singUpDto) {
        if (isRoleExist(singUpDto.getRole())) {
            return Result.success(singUpDto);
        } else {
            return Result.failure("Incorrect role");
        }
    }

    private static boolean isRoleExist(String userRole) {
        return Arrays.stream(Role.values())
                .map(role -> role.name().replace("ROLE_", ""))
                .anyMatch(roleName -> roleName.equalsIgnoreCase(userRole));
    }
}
