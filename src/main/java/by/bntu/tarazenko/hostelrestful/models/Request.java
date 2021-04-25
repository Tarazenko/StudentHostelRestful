package by.bntu.tarazenko.hostelrestful.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private int room;

    @NotBlank
    @Size(max = 255)
    private String content;

    @NotBlank
    @Size(max = 255)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
