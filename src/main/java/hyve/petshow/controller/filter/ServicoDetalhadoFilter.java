package hyve.petshow.controller.filter;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicoDetalhadoFilter {
    private Integer tipoServicoId;
    private String ordenacao;
    private Boolean possuiAdicionais;
    private Integer mediaAvaliacao;
    private BigDecimal menorPreco;
    private BigDecimal maiorPreco;
}
