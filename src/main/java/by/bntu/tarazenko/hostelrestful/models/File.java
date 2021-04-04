package by.bntu.tarazenko.hostelrestful.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String type;

  @Lob
  private byte[] data;

  public File(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }
}
