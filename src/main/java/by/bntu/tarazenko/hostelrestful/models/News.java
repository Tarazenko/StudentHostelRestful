package by.bntu.tarazenko.hostelrestful.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Table(	name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    @Size(max = 50)
    private String preview;

    @NotBlank
    @Size(max = 500)
    private String text;

    /*    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(	name = "documents_files",
                joinColumns = @JoinColumn(name = "documents_id"),
                inverseJoinColumns = @JoinColumn(name = "files_id"))*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "files_id", referencedColumnName = "id")
    private File file;
}
