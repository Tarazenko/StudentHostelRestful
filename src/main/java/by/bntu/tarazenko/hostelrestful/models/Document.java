package by.bntu.tarazenko.hostelrestful.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(	name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;

/*    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "documents_files",
            joinColumns = @JoinColumn(name = "documents_id"),
            inverseJoinColumns = @JoinColumn(name = "files_id"))*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "files_id", referencedColumnName = "id")
    private File file;
}
