package hyve.petshow.domain;

import lombok.AllArgsConstructor;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity(name = "servico_detalhado_agendamento")
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDetalhadoAgendamento implements Serializable {
    private static final long serialVersionUID = 368702712017722297L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_agendamento", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Agendamento agendamento;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_servico_detalhado", referencedColumnName = "id", nullable = false)
    private ServicoDetalhado servicoDetalhado;
<<<<<<< HEAD
=======
    
    public ServicoDetalhadoAgendamento(Agendamento agendamento, ServicoDetalhado servicoDetalhado){
        this.agendamento = agendamento;
        this.servicoDetalhado = servicoDetalhado;
    }
>>>>>>> 205f6d1885fb0d324b30f2dcbf89d541b8164383
}
