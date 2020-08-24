package hyve.petshow.controller.representation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TesteValidacaoRepresentation {
    private Long idTeste;
    private Long idValidacao;
    private String teste;
    private String validacao;
}
