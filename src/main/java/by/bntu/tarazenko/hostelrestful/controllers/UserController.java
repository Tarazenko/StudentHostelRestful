package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.UserConverter;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.models.dtos.UserDTO;
import by.bntu.tarazenko.hostelrestful.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId){
        User user = userService.getById(userId);
        log.debug("User - {}", user);
        return  ResponseEntity.ok(userConverter.toUserDTO(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(){
        List<User> users = userService.getAll();
        log.debug("Users count - {}", users.size());

        List<UserDTO> userDTOs = new ArrayList<>();

        users.stream().forEach(user -> {
            userDTOs.add(userConverter.toUserDTO(user));
        });

        return  ResponseEntity.ok(userDTOs);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId){
        log.debug("Start delete user with id - {}", userId);
        userService.deleteById(userId);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody User user){
        user.setId(userId);
        log.debug("Update user - {}", user);
        User updateUser = userService.update(user);
        log.debug("After update user - {}", updateUser);
        return  ResponseEntity.ok(userConverter.toUserDTO(updateUser));
    }
}
