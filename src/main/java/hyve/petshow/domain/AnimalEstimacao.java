package hyve.petshow.domain;

import javax.persistence.*;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
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
