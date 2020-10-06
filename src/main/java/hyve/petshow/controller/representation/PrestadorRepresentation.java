package hyve.petshow.controller.representation;

import java.util.List;

import lombok.Data;

@Data
public class PrestadorRepresentation extends ContaRepresentation {
    private List<ServicoDetalhadoRepresentation> servicos;
    private String descricao;
}