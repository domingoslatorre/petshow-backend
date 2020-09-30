package hyve.petshow.mock.entidades;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.AvaliacaoInfo;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;

public class AvaliacaoMock {
	public static Avaliacao geraAvaliacao() {
		Servico tipo = new Servico();
		tipo.setId(1l);
		tipo.setNome("Banho");
		tipo.setDescricao("Banho");
		
		ServicoDetalhado servicoAvaliado = new ServicoDetalhado();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setPreco(BigDecimal.valueOf(30.5));
		servicoAvaliado.setTipo(tipo);
		
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Teste");
		
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setServicoAvaliado(servicoAvaliado);
		AvaliacaoInfo info = new AvaliacaoInfo();
		info.setAtencao(5);
		info.setQualidadeProdutos(5);
		info.setCustoBeneficio(5);
		info.setInfraestrutura(5);
		info.setQualidadeServico(5);
		info.setComentario("Muito bom");
		avaliacao.setAvaliacaoInfo(info);
		avaliacao.setCliente(cliente);
		
		return avaliacao;
	}
	
	public static List<Avaliacao> geraListaAvaliacao() {
		Servico tipo = new Servico();
		tipo.setId(1l);
		tipo.setNome("Banho");
		tipo.setDescricao("Banho");
		
		ServicoDetalhado servicoAvaliado = new ServicoDetalhado();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setPreco(BigDecimal.valueOf(30.5));
		servicoAvaliado.setTipo(tipo);
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Teste");
		return Stream.of(new Avaliacao(), new Avaliacao(), new Avaliacao())
				.map(avaliacao -> {
					avaliacao.setServicoAvaliado(servicoAvaliado);
					AvaliacaoInfo info = new AvaliacaoInfo();
					info.setAtencao(geraNota());
					info.setQualidadeProdutos(geraNota());
					info.setCustoBeneficio(geraNota());
					info.setInfraestrutura(geraNota());
					info.setQualidadeServico(geraNota());
					info.setComentario("Muito bom");
					avaliacao.setAvaliacaoInfo(info);
					avaliacao.setCliente(cliente);
					return avaliacao;
				}).collect(Collectors.toList());
	}
	
	private static Integer geraNota() {
		return new Random().ints(1,0,5).boxed().findFirst().get();
	}
	
}
