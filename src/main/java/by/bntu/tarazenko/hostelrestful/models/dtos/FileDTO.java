package by.bntu.tarazenko.hostelrestful.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDTO {
  private String name;
  private String url;
  private String type;
  private Long id;
  private long size;
}
