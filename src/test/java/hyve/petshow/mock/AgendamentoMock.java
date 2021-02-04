package hyve.petshow.mock;

import hyve.petshow.domain.AdicionalAgendamento;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.domain.AnimalEstimacaoAgendamento;
import hyve.petshow.domain.embeddables.Endereco;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static hyve.petshow.mock.AdicionalMock.criaAdicional;
import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static hyve.petshow.mock.AvaliacaoMock.avaliacao;
import static hyve.petshow.mock.ClienteMock.cliente;
import static hyve.petshow.mock.PrestadorMock.prestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static hyve.petshow.mock.StatusAgendamentoMock.statusAgendamento;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

public class AgendamentoMock {
    public static Agendamento agendamento(){
        var agendamento = new Agendamento();
        var animalEstimacaoAgendamento = new AnimalEstimacaoAgendamento(agendamento, animalEstimacao());
        var adicionalAgendamento = new AdicionalAgendamento(agendamento, criaAdicional(1L));

        agendamento.setEndereco(new Endereco());
        agendamento.setData(LocalDateTime.now());
        agendamento.setPrecoFinal(BigDecimal.TEN);
        agendamento.setPrestador(prestador());
        agendamento.setStatus(statusAgendamento());
        agendamento.setAnimaisAtendidos(Arrays.asList(animalEstimacaoAgendamento));
        agendamento.setServicoDetalhado(servicoDetalhado());
        agendamento.setAuditoria(geraAuditoriaInsercao(Optional.of(1L)));
        agendamento.setCliente(cliente());
        agendamento.setComentario("teste");
        agendamento.setAdicionais(Arrays.asList(adicionalAgendamento));
        agendamento.setAvaliacao(avaliacao());
        agendamento.setId(1L);

        return agendamento;
    }
}
