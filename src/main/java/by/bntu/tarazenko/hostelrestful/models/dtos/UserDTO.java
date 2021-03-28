package by.bntu.tarazenko.hostelrestful.models.dtos;

import by.bntu.tarazenko.hostelrestful.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    Long id;
    String username;
    String email;
    String name;
    String surname;
    String patronymic;
    List<String> roles;
}
