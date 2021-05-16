package by.bntu.tarazenko.hostelrestful.models.dtos;

import by.bntu.tarazenko.hostelrestful.models.Status;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestDTO {

    private Long id;
    private int room;
    private String content;
    private String comment;
    private Status status;
    private UserDTO user;
}
