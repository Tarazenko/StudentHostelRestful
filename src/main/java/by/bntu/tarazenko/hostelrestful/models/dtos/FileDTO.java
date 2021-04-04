package by.bntu.tarazenko.hostelrestful.models.dtos;

import lombok.Data;

@Data
public class FileDTO {
  private String name;
  private String url;
  private String type;
  private long size;

  public FileDTO(String name, String url, String type, long size) {
    this.name = name;
    this.url = url;
    this.type = type;
    this.size = size;
  }
}
