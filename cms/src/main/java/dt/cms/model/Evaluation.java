package dt.cms.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Evaluation {
    @Id
    @GeneratedValue
    private int id;
    private String result;
    private String justification;

    @ManyToOne(cascade = CascadeType.ALL)
    private Submission submission;

    @ManyToOne(cascade = CascadeType.ALL)
    private User reviewer;
}
