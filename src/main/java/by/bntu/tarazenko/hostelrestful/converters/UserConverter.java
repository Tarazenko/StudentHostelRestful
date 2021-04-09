package by.bntu.tarazenko.hostelrestful.converters;

import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.models.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserConverter {
    public UserDTO toUserDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .role(user.getRole().toString())
                .build();
    }

}
