package hyve.petshow.controller.representation;

import lombok.Data;

import java.util.List;

@Data
public class PrestadorRepresentation extends ContaRepresentation {
    private List<ServicoDetalhadoRepresentation> servicos;
    private String descricao;
}