package com.davv1d.controller;

import com.davv1d.domain.user.EmailUpdaterDto;
import com.davv1d.domain.user.UserDto;
import com.davv1d.errors.UsernameNotFoundException;
import com.davv1d.mapper.user.EmailUpdaterMapper;
import com.davv1d.mapper.user.UserMapper;
import com.davv1d.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserDbService userDbService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUpdaterMapper emailUpdaterMapper;

    @GetMapping("/users")
    public List<UserDto> fetchAll() {
        return userMapper.mapToUserDtoList(userDbService.fetchAll());
    }

    @GetMapping(value = "/users/{username}")
    public UserDto fetchUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return userMapper.mapToUserDto(userDbService.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found name " + username)));
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam String username) {
        userDbService.deleteByUsername(username);
    }

    @PutMapping("/users")
    public void changeUserEmail(@RequestBody EmailUpdaterDto emailUpdaterDto) {
        userDbService.changeUserEmail(emailUpdaterMapper.mapToEmailUpdater(emailUpdaterDto));
    }
}
