package hyve.petshow.mock.entidades;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hyve.petshow.domain.Avaliacao;
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
		avaliacao.setAtencao(10);
		avaliacao.setQualidadeProdutos(10);
		avaliacao.setCustoBeneficio(10);
		avaliacao.setInfraestrutura(10);
		avaliacao.setQualidadeServico(10);
		avaliacao.setComentario("Muito bom");
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
					avaliacao.setAtencao(geraNota());
					avaliacao.setQualidadeProdutos(geraNota());
					avaliacao.setCustoBeneficio(geraNota());
					avaliacao.setInfraestrutura(geraNota());
					avaliacao.setQualidadeServico(geraNota());
					avaliacao.setComentario("Muito bom");
					avaliacao.setCliente(cliente);
					return avaliacao;
				}).collect(Collectors.toList());
	}
	
	private static Integer geraNota() {
		return new Random().ints(1,0,10).boxed().findFirst().get();
	}
	
}
