package hyve.petshow.domain;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity(name = "animal_estimacao")
public class AnimalEstimacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String foto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_animal_estimacao")
    private TipoAnimalEstimacao tipo;
}
