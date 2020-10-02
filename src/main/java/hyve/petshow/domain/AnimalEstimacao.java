package hyve.petshow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "animal_estimacao")
public class AnimalEstimacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String foto;
    @ManyToOne
    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
    private Cliente dono;
    @Column(name = "fk_tipo_animal_estimacao")
    private Integer tipo;
}
