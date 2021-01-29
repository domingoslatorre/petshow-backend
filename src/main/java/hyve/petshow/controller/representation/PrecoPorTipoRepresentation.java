package hyve.petshow.controller.representation;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PrecoPorTipoRepresentation {
    private TipoAnimalEstimacaoRepresentation tipoAnimal;
    private BigDecimal preco;
}
