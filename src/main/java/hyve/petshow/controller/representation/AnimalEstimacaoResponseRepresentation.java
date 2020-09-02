package hyve.petshow.controller.representation;

import lombok.Data;

@Data
public class AnimalEstimacaoResponseRepresentation {
    private Long id;
    private Boolean sucesso;
    private String mensagem;
}
