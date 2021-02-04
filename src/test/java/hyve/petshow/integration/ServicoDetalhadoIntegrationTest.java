package hyve.petshow.integration;

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.filter.ServicoDetalhadoFilter;
import hyve.petshow.controller.representation.AdicionalRepresentation;
import hyve.petshow.controller.representation.ComparacaoWrapper;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.*;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;
import hyve.petshow.repository.*;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

import static hyve.petshow.mock.ContaMock.contaCliente;
import static hyve.petshow.mock.ContaMock.contaPrestador;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoDetalhadoIntegrationTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private ServicoDetalhadoRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;
	@Autowired
	private ServicoDetalhadoService service;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private AdicionalRepository adicionalRepository;
	@Autowired
	private ServicoDetalhadoConverter converter;
	
	private String url;
	
	private Cliente cliente;
	private Servico tipoServico;
	private ServicoDetalhado servico;
	
	@BeforeEach
	public void init() {
		tipoServico = new Servico();
		tipoServico.setNome("Banho e tosa");
		servicoRepository.save(tipoServico);
		
		var prestador = new Prestador(contaPrestador());
		prestador.setId(null);
		prestadorRepository.save(prestador);
		
		cliente = new Cliente(contaCliente());
		cliente.setId(null);
		clienteRepository.save(cliente);
		
		servico = new ServicoDetalhado();
		servico.setTipo(tipoServico);
		servico.setPrestadorId(prestador.getId());
		
		url = "http://localhost:" + port + "/prestador/" + prestador.getId() + "/servico-detalhado";
	}
	
	@AfterEach
	public void limpaRepositorio() {
		adicionalRepository.deleteAll();
		avaliacaoRepository.deleteAll();
		repository.deleteAll();
		servicoRepository.deleteAll();
		prestadorRepository.deleteAll();
		clienteRepository.deleteAll();
	}
	
	@Test
	public void deve_retornar_lista_com_servicos() {
		service.adicionarServicoDetalhado(servico);
		var uri = UriComponentsBuilder.fromHttpUrl(this.url)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_erro_de_lista_vazia() throws Exception {
		var uri = UriComponentsBuilder.fromHttpUrl(this.url)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_por_tipo_de_servico() throws Exception {
		service.adicionarServicoDetalhado(servico);
		var httpUrl = "http://localhost:"+this.port+"/servico-detalhado/filtro";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
		.queryParam("pagina", 0)
		.queryParam("quantidadeItens", 5)
		.toUriString();
		var filter = new ServicoDetalhadoFilter();

		filter.setTipoServicoId(tipoServico.getId());
		
		var response = template.postForEntity(uri, filter, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrar_tipo() throws Exception {
		var httpUrl = "http://localhost:"+this.port+"/servico-detalhado/filtro";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		var filter = new ServicoDetalhadoFilter();
		filter.setTipoServicoId(tipoServico.getId() + 1);

		var response = template.postForEntity(uri, filter, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_lista_vazia_de_avaliacoes() {
		service.adicionarServicoDetalhado(servico);
		var httpUrl = "http://localhost:"+this.port + "/servico-detalhado/"+servico.getId()+"/avaliacoes";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void deve_adicionar_servico_detalhado() throws Exception {
		var representation = converter.toRepresentation(servico);
		var uri = new URI(this.url);
		var body = new HttpEntity<>(representation, new HttpHeaders());
		var response = template.postForEntity(uri, body, ServicoDetalhadoRepresentation.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}
	
	@Test
	public void deve_remover_servico_detalhado() throws Exception {
		service.adicionarServicoDetalhado(servico);
		var uri = new URI(this.url + "/"+servico.getId());
		template.delete(uri);
		
		assertFalse(repository.existsById(servico.getId()));
	}
	
	@Test
	public void deve_buscar_servico() throws Exception {
		service.adicionarServicoDetalhado(servico);
		var uri = new URI(this.url + "/"+servico.getId());
		var response = template.getForEntity(uri, ServicoDetalhadoRepresentation.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_atualizar_servico() throws Exception {
		var servicoAdd = service.adicionarServicoDetalhado(servico);
		var uri = new URI(this.url + "/"+servico.getId());
		var tipoServico = new Servico();
		tipoServico.setNome("Banho e tosa");
		servicoAdd.setTipo(tipoServico);
		var body = new HttpEntity<>(servicoAdd, new HttpHeaders());
		template.exchange(uri, HttpMethod.PUT, body, Void.class);
		
		var busca = repository.findById(servicoAdd.getId()).get();
		var tipo = busca.getTipo();
		assertEquals("Banho e tosa", tipo.getNome());
	}
	
	@Test
	public void deve_retornar_lista_de_avaliacoes() {
		var servicoAdd = service.adicionarServicoDetalhado(servico);
		var avaliacao = new Avaliacao();
		avaliacao.setServicoAvaliadoId(servicoAdd.getId());
		avaliacao.setCliente(cliente);
		avaliacao.setCriteriosAvaliacao(new CriteriosAvaliacao());
		
		avaliacaoRepository.save(avaliacao);
		
		var httpUrl = "http://localhost:"+this.port + "/servico-detalhado/"+servico.getId()+"/avaliacoes";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void deve_criar_adicionais_para_servicos() throws Exception {
		var servicoAdd = service.adicionarServicoDetalhado(servico);
		var adicional = AdicionalRepresentation.builder()
						.servicoDetalhadoId(servicoAdd.getId())
						.nome("Teste adicional")
						.preco(BigDecimal.valueOf(23))
						.build();
		
		var uri = new URI(this.url + "/" + servicoAdd.getId() + "/adicional");
		var entity = new HttpEntity<>(adicional, new HttpHeaders());
		var response = template.postForEntity(uri, entity, AdicionalRepresentation.class);
	
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		var busca = adicionalRepository.findById(response.getBody().getId());
		assertTrue(busca.isPresent());
		var buscaServico = repository.findById(servicoAdd.getId()).get();
		assertTrue(buscaServico.getAdicionais().contains(busca.get()));
	}
	
	@Test
	public void deve_retornar_lista_com_servicos_para_comparacao() {
		service.adicionarServicoDetalhado(servico);
		var uri = UriComponentsBuilder.fromHttpUrl("http://localhost:"+this.port+"/servico-detalhado")
				.queryParam("ids", servico.getId())
				.toUriString();
		
		var response = template.exchange(uri, HttpMethod.GET, null, ComparacaoWrapper.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().getComparacoes().isEmpty());
	}
}
