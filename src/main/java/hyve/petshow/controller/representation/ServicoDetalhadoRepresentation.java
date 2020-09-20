package hyve.petshow.controller.representation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import hyve.petshow.domain.Servico;
import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ServicoDetalhadoRepresentation {
    private BigDecimal preco;
    private ServicoRepresentation tipo;
    private List<TipoAnimalEstimacao> animaisAceitos;
    private PrestadorRepresentation prestador;
}
