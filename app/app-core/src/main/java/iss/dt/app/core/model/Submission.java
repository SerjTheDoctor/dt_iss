package iss.dt.app.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "submission")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    @OneToOne(cascade = CascadeType.ALL)
    private Paper paper;

    @ManyToOne(fetch = FetchType.EAGER)
    private Conference conference;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submission")
    private List<Evaluation> evaluations;

    public Submission(Long id, User convertDtoToModel, Paper convertDtoToModel1) {
        this.id=id;
        this.author=convertDtoToModel;
        this.paper=convertDtoToModel1;
    }
}
