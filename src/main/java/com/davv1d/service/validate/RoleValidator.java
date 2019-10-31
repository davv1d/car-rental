package com.davv1d.service.validate;

import com.davv1d.domain.UserDto;
import com.davv1d.domain.constant.Role;
import com.davv1d.functional.Result;

import java.util.Arrays;

public class RoleValidator {

    public static Result<UserDto> roleValidator(final UserDto userDto) {
        if (isRoleExist(userDto.getRole())) {
            return Result.success(userDto);
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
