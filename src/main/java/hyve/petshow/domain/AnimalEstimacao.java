package hyve.petshow.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;

@Data
@Entity(name = "animal_estimacao")
public class AnimalEstimacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String foto;
    private String porte;
    private String pelagem;
    @Embedded
    private Auditoria auditoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_animal_estimacao")
    private TipoAnimalEstimacao tipo;
    @Column(name = "fk_conta")
    private Long donoId;
}
