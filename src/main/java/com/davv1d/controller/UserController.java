package com.davv1d.controller;

import com.davv1d.domain.user.EmailUpdaterDto;
import com.davv1d.domain.user.UserDto;
import com.davv1d.errors.UsernameNotFoundException;
import com.davv1d.mapper.user.EmailUpdaterMapper;
import com.davv1d.mapper.user.UserMapper;
import com.davv1d.service.db.UserDbService;
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

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        return userMapper.mapToUserDtoList(userDbService.getAll());
    }

    @GetMapping(value = "/users", params = "username")
    public UserDto getUserByUsername(@RequestParam String username) throws UsernameNotFoundException {
        return userMapper.mapToUserDto(userDbService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found name " + username)));
    }

    @DeleteMapping(value = "/users", params = "username")
    public void deleteUser(@RequestParam String username) {
        userDbService.deleteByUsername(username);
    }

    @PutMapping("/users")
    public void changeUserEmail(@RequestBody EmailUpdaterDto emailUpdaterDto) {
        userDbService.changeUserEmail(emailUpdaterMapper.mapToEmailUpdater(emailUpdaterDto));
    }
}
