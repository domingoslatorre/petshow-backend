package hyve.petshow.controller.representation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidacaoRepresentation {
    private Long id;
    private String validacao;
}
