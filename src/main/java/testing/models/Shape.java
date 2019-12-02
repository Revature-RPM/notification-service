package testing.models;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "kind")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Shape {

    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
