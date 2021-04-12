package by.bntu.tarazenko.hostelrestful.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDTO {
    Long id;
    String title;
    String preview;
    String text;
    FileDTO file;
}
