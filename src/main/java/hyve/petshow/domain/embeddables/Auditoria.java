package hyve.petshow.domain.embeddables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;

@Data
@Embeddable
@NoArgsConstructor
public class Auditoria {
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long usuarioCriacao;
    private String flagAtivo;
    
    public Boolean isAtivo() {
    	return ATIVO.equals(getFlagAtivo());
    }
}
