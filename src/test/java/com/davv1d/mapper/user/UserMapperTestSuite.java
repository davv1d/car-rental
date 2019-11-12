package com.davv1d.mapper.user;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.UserDto;
import com.davv1d.domain.user.login.SignUpDto;
import com.davv1d.domain.user.role.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestSuite {
    private User user;
    @Autowired
    private UserMapper userMapper;

    @Before
    public void init() {
        user = new User("username", "password", "test@test.com", Role.ROLE_CLIENT.name());
    }

    @Test
    public void testMapUserToUserDto() {
        //Given
        //When
        UserDto userDto = userMapper.mapToUserDto(user);
        //Then
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole(), userDto.getRole());
    }

    @Test
    public void testMapListOfUsersToListOfUsersDto() {
        //Given
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(user);
        listOfUsers.add(user);
        //When
        List<UserDto> listOfUsersDto = userMapper.mapToUserDtoList(listOfUsers);
        //Then
        assertEquals(listOfUsers.size(), listOfUsersDto.size());
        assertEquals(listOfUsers.get(0).getUsername(), listOfUsersDto.get(0).getUsername());
    }

    @Test
    public void shouldFetchSignUpDtoAndReturnUser() {
        //Given
        SignUpDto singUpDto = new SignUpDto("username", "password", "test@test.com", Role.ROLE_ADMIN.name());
        //When
        User user = userMapper.mapToUser(singUpDto);
        //Then
        assertEquals(singUpDto.getUsername(), user.getUsername());
        assertNotEquals(singUpDto.getPassword(), user.getPassword());
        assertEquals(singUpDto.getEmail(), user.getEmail());
        assertEquals(singUpDto.getRole(), user.getRole());
    }
}