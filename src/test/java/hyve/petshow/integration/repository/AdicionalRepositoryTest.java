package hyve.petshow.integration.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Adicional;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ContaMock;
import hyve.petshow.repository.AdicionalRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AdicionalRepositoryTest {
	@Autowired
	private ServicoDetalhadoRepository servicoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;
	@Autowired
	private ServicoRepository tipoRepository;
	@Autowired
	private AdicionalRepository repository;
	
	private ServicoDetalhado servico;
	private Servico tipo;
	private Prestador prestador;
	private Adicional adicional;
	
	@BeforeEach
	public void init() {
		prestador = new Prestador(ContaMock.contaPrestador());
		prestador.setId(null);
		prestadorRepository.save(prestador);
		
		tipo = new Servico();
		tipo.setNome("Teste");
		tipoRepository.save(tipo);
		
		servico = new ServicoDetalhado();
		servico.setTipo(tipo);
		servico.setPrestadorId(prestador.getId());
		servico.setPreco(BigDecimal.valueOf(25));
		servicoRepository.save(servico);
		
		adicional = Adicional.builder().nome("Teste adicional")
					.descricao("Teste").idServicoDetalhado(servico.getId())
					.preco(BigDecimal.valueOf(10))
					.build();
		
	}
	
	@AfterEach
	public void delete() {
		repository.deleteAll();
		servicoRepository.deleteAll();
		tipoRepository.deleteAll();
		prestadorRepository.deleteAll();
	}
	
	@Test
	public void deve_salvar_adicional() {
		repository.save(adicional);
		
		assertTrue(repository.findById(adicional.getId()).isPresent());
	}
	
	@Test
	public void deve_retornar_adicionais_ao_buscar_servico() {
		repository.save(adicional);
		var busca = servicoRepository.findById(servico.getId()).get();
		assertTrue(busca.getAdicionais().contains(adicional));
	}

}
