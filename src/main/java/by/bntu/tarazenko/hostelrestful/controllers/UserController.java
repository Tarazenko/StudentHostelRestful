package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.UserConverter;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.models.dtos.UserDTO;
import by.bntu.tarazenko.hostelrestful.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserConverter userConverter;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable("userId") long userId){
        User user = userService.getUser(userId);
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
}
