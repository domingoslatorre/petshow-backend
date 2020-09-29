package hyve.petshow.unit.controller;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.repository.PrestadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PrestadorControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private PrestadorRepository repository;

    private ContaRepresentation contaMock;

    private String url;

    @BeforeEach
    public void init() {
        url = "http://localhost:" + port + "/prestador";

    }

    @BeforeEach
    public void initMock() {
        contaMock = new ContaRepresentation();
        Login login = new Login();
        login.setEmail("teste@teste.com");
        login.setSenha("555555555555");
        contaMock.setLogin(login);
        contaMock.setCpf("44444444444");
    }

    @Test
    @Order(1)
    public void deve_salvar_conta() throws URISyntaxException {
        URI uri = new URI(this.url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

        ResponseEntity<ContaRepresentation> response = template.postForEntity(uri, request, ContaRepresentation.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody().getLogin(), contaMock.getLogin());
        assertTrue(repository.existsById(response.getBody().getId()));
    }

    @Test
    @Order(2)
    public void deve_retornar_erro_por_email_repetido() throws URISyntaxException {
        URI uri = new URI(this.url);
        contaMock.setCpf("632478509");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

        ResponseEntity<String> response = template.postForEntity(uri, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    public void deve_retornar_erro_por_cpf_repetido() throws URISyntaxException {
        URI uri = new URI(this.url);
        Login login = new Login();
        login.setEmail("oqiwuefnajs");
        login.setSenha("834hufakvdsn");
        contaMock.setLogin(login);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

        ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    @Order(4)
    public void deve_retornar_por_login() throws URISyntaxException {
        URI uri = new URI(this.url + "/login");
        Login login = contaMock.getLogin();

        HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());

        ResponseEntity<PrestadorRepresentation> response = template.postForEntity(uri, request, PrestadorRepresentation.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(5)
    public void deve_retornar_nao_encontrado() throws URISyntaxException {
        URI uri = new URI(this.url + "/login");

        Login login = new Login();
        login.setEmail("aslkdjgs@aklsdjg.com");
        login.setSenha("ASDOHIGJKLAjh0oiq");

        HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());

        ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    public void deve_retornar_excecao() throws URISyntaxException {
        URI uri = new URI(this.url);
        ContaRepresentation contaMock = new ContaRepresentation();

        HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, new HttpHeaders());

        ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
//
//    @Test
//    @Order(7)
//    public void deve_adicionar_servico_detalhado() throws URISyntaxException {
//        URI uri = new URI(this.url);
//
//        Conta byLogin = repository.findByLogin(contaMock.getLogin()).get();
//        PrestadorRepresentation conta = new PrestadorRepresentation();
//        conta.setId(byLogin.getId());
//        Login login = new Login();
//        login.setEmail("teste@teste.com");
//        login.setSenha("teste1234");
//        conta.setLogin(login);
//        conta.setCpf("44444444444");
//
////        List<ServicoDetalhadoRepresentation> servicosDetalhados = new ArrayList<ServicoDetalhadoRepresentation>();
////        ServicoDetalhadoRepresentation servicoDetalhado = new ServicoDetalhadoRepresentation();
////        servicosDetalhados.setNome("Aslkajdgads");
////        servicosDetalhados.setTipo();
////        servicosDetalhados.add(servicoDetalhado);
////        conta.setServicoDetalhado(servicoDetalhado);
//
//        HttpEntity<PrestadorRepresentation> request = new HttpEntity<>(conta, new HttpHeaders());
//
//        ResponseEntity<PrestadorRepresentation> response = template.exchange(uri, HttpMethod.PUT, request,
//                PrestadorRepresentation.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        PrestadorRepresentation body = response.getBody();
////        assertTrue(body.getServicoDetalhado().stream().filter(el -> el.getNome().equals(servicoDetalhado.getNome())).findFirst()
////                .isPresent());
//    }
//
//
//    @Test
//    @Order(8)
//    public void deve_adicionar_servico_detalhado_a_lista() throws URISyntaxException {
//        URI uri = new URI(this.url);
//
//        PrestadorRepresentation prestador = new PrestadorRepresentation();
//        Login login = new Login();
//        login.setEmail("testeCalebe@teste.com");
//        login.setSenha("testete1234");
//        prestador.setLogin(login);
//
//        prestador.setCpf("12345678909");
//
//        prestador.setNome("Joao");
////        List<ServicoDetalhadoRepresentation> servicosDetalhados = new ArrayList<ServicoDetalhadoRepresentation>();
////        ServicoDetalhadoRepresentation servicoDetalhado = new ServicoDetalhadoRepresentation();
////        servicoDetalhado.setNome("pedrinho");
////        servicoDetalhado.setTipo();
////        servicoDetalhado.setFoto("");
////        servicosDetalhados.add(servicoDetalhado);
////        prestador.setServicoDetalhado(servicosDetalhados);
//
//        HttpEntity<PrestadorRepresentation> requestPost = new HttpEntity<PrestadorRepresentation>(prestador, new HttpHeaders());
//        ResponseEntity<PrestadorRepresentation> responsePost = template.postForEntity(uri, requestPost, PrestadorRepresentation.class);
//
//        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
//
//        HttpEntity<Login> requestLogin = new HttpEntity<Login>(prestador.getLogin(), new HttpHeaders());
//        ResponseEntity<PrestadorRepresentation> responseLogin = template.postForEntity(new URI(this.url+"/login"), requestLogin, PrestadorRepresentation.class);
//
//        assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
//
//        prestador = responseLogin.getBody();
////        servicosDetalhados = prestador.getServicoDetalhado();
//
////        ServicoDetalhadoRepresentation servicoDetalhado2 = new ServicoDetalhadoRepresentation();
////        servicoDetalhado2.setNome("felipinho");
////        servicoDetalhado2.setTipo();
////        servicoDetalhado2.setFoto("");
////        servicosDetalhados.add(servicoDetalhado2);
//
////        prestador.setServicoDetalhado(servicosDetalhados);
//
//        HttpEntity<PrestadorRepresentation> requestPut = new HttpEntity<PrestadorRepresentation>(prestador, new HttpHeaders());
//        ResponseEntity<PrestadorRepresentation> responsePut = template.exchange(uri, HttpMethod.PUT, requestPut,	PrestadorRepresentation.class);
//        responsePut.getBody();
//        assertEquals(HttpStatus.OK, responsePut.getStatusCode());
////        assertEquals(2, responsePut.getBody().getServicoDetalhado().size());
//
//    }
}
