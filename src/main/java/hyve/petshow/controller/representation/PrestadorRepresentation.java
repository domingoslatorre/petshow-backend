package hyve.petshow.controller.representation;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrestadorRepresentation extends ContaRepresentation {
    private List<ServicoDetalhadoRepresentation> servicos;
    private String descricao;
}