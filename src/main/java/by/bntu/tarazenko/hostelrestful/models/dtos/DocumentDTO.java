package by.bntu.tarazenko.hostelrestful.models.dtos;

import by.bntu.tarazenko.hostelrestful.models.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDTO {
    Long id;
    String name;
    Category category;
    FileDTO file;
}
