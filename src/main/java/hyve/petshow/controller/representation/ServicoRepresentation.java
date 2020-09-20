package hyve.petshow.controller.representation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import hyve.petshow.domain.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ServicoRepresentation {
    private String nome;
    private String descricao;
}