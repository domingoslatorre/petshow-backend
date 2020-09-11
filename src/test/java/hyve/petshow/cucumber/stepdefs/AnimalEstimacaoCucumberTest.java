package hyve.petshow.cucumber.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hyve.petshow.mock.AnimalEstimacaoMock;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;

@DataJpaTest
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AnimalEstimacaoCucumberTest {
	@LocalServerPort
	private Integer port;
	
    private ResponseEntity<?> responseEntity;

    @Dado("que acesso {string}{string} adequadamente com metodo {string} e corpo {string}")
    public void que_acesso_endereco_adequadamente_com_metodo(String endereco, String pathVariable, String metodo, String corpo) {
        var restTemplate = new RestTemplate();
        var uri = URI.create("http://localhost:".concat(port.toString()).concat(endereco).concat(pathVariable));
        var body = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var method = HttpMethod.valueOf(metodo);
        var requestEntity = corpo.isEmpty() ?
                new RequestEntity(method, uri):
                new RequestEntity(body, method, uri);

       responseEntity = restTemplate.exchange(requestEntity, Object.class);
    }

    @Entao("devo receber como retorno um status {int}")
    public void devo_receber_como_retorno_um_status(Integer statusHttp) {
        var expectedStatus = HttpStatus.valueOf(statusHttp);
        var actualStatus = responseEntity.getStatusCode();

        assertEquals(expectedStatus, actualStatus);
    }

    @E("a resposta deve ser {string}")
    public void um_objeto_do_tipo_objeto_no_corpo_da_resposta(String expectedResponse) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var actualResponse = objectMapper.writeValueAsString(responseEntity.getBody());

        assertEquals(expectedResponse, actualResponse);
    }
}
