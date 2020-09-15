package hyve.petshow.controller.representation;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import lombok.Data;

@Data
public class AnimalEstimacaoRepresentation {
    private Long id;
    private String nome;
    private String foto;
    private TipoAnimalEstimacao tipo;
}
