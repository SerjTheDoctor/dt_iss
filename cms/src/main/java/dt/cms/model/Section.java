package dt.cms.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table (name = "section")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Section {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User chair;

    private String room;

    // TODO: what is this?
    /*
    @OneToMany
    @Column(name = "speakers", nullable = false)
    private List<User> speakers;

    @OneToMany
    @Column(name = "listener", nullable = false)
    private List<User> listener;*/

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;
}