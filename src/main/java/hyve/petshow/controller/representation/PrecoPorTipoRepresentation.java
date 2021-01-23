package hyve.petshow.controller.representation;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PrecoPorTipoRepresentation {
    private TipoAnimalEstimacaoRepresentation tipoAnimal;
    private BigDecimal preco;
}
