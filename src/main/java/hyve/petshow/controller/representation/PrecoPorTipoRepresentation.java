package hyve.petshow.controller.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrecoPorTipoRepresentation {
    private TipoAnimalEstimacaoRepresentation tipoAnimal;
    private BigDecimal preco;
    private Boolean ativo;
}
