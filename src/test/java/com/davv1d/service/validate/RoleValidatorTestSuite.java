package com.davv1d.service.validate;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoleValidatorTestSuite {

    @Test
    public void testIsRoleExist() {
        //Given
        String role1 = "client";
        String role2 = "CLIENT";
        String role3 = "clIEnt";
        String role4 = "admin";
        String role5 = "error";
        //When
        boolean roleExist1 = RoleValidator.isRoleExist(role1);
        boolean roleExist2 = RoleValidator.isRoleExist(role2);
        boolean roleExist3 = RoleValidator.isRoleExist(role3);
        boolean roleExist4 = RoleValidator.isRoleExist(role4);
        boolean roleExist5 = RoleValidator.isRoleExist(role5);
        //Then
        assertTrue(roleExist1);
        assertTrue(roleExist2);
        assertTrue(roleExist3);
        assertTrue(roleExist4);
        assertFalse(roleExist5);
    }
}