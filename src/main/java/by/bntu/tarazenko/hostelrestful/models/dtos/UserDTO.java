package by.bntu.tarazenko.hostelrestful.models.dtos;

import by.bntu.tarazenko.hostelrestful.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    Long id;
    String username;
    String name;
    String surname;
    String patronymic;
    String email;
    String role;
}
