package com.davv1d.controller;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.UserDto;
import com.davv1d.errors.UsernameNotFoundException;
import com.davv1d.mapper.user.UserMapper;
import com.davv1d.service.db.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserDbService userDbService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        return userMapper.mapToUserDtoList(userDbService.getAll());
    }

    @GetMapping(value = "/users", params = "username")
    public UserDto getUserByUsername(@RequestParam String username) throws UsernameNotFoundException {
        return userMapper.mapToUserDto(userDbService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found name " + username)));
    }

    @GetMapping(value = "/loggedUser")
    public UserDto getLoggedUser(Principal principal) {
        return userMapper.mapToUserDto(userDbService.getUserByUsername(principal.getName()).get());
    }

    @DeleteMapping(value = "/users", params = "username")
    public void deleteUser(@RequestParam String username) {
        userDbService.deleteByUsername(username);
    }

    @PutMapping("/users/email")
    public void changeUserEmail(@RequestBody String email, Principal principal) {
        EmailUpdater emailUpdater = new EmailUpdater(principal.getName(), email);
        userDbService.changeUserEmail(emailUpdater);
    }
}
