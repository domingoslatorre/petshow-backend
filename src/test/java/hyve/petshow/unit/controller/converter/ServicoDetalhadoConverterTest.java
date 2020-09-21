package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ServicoDetalhadoMock;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")

public class ServicoDetalhadoConverterTest {
    @Autowired
    private ServicoDetalhadoConverter converter;

    @Test
    public void deve_converter_para_representation(){
        //dado
        var expected = ServicoDetalhadoMock.representation();
        BigDecimal p = new BigDecimal(70);
    	expected.setPreco(p);
        var servicoDetalhado = ServicoDetalhadoMock.servicoDetalhado();

        //quando
        var actual = converter.toRepresentation(servicoDetalhado);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_converter_para_domain(){
        //dado
        var expected = ServicoDetalhadoMock.servicoDetalhado();
        var servicoDetalhadoRepresentation = ServicoDetalhadoMock.representation();
        BigDecimal p = new BigDecimal(70);
        servicoDetalhadoRepresentation.setPreco(p);
        //quando
        var actual = converter.toDomain(servicoDetalhadoRepresentation);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_converter_para_lista_de_representation(){
        //dado
        ServicoDetalhadoRepresentation servicoDetalhadoRepresentation = ServicoDetalhadoMock.representation();
        BigDecimal p = new BigDecimal(70);
        servicoDetalhadoRepresentation.setPreco(p);
        var expected = Arrays.asList(servicoDetalhadoRepresentation);
        var servicosDetalhados = Arrays.asList(ServicoDetalhadoMock.servicoDetalhado());

        //quando
        var actual = converter.toRepresentationList(servicosDetalhados);

        //entao
        assertEquals(expected, actual);
    }
    
    @Test
    public void deve_converter_para_lista_de_domains(){
        //dado
        ServicoDetalhado servicoDetalhado = ServicoDetalhadoMock.servicoDetalhado();
        BigDecimal p = new BigDecimal(70);
        servicoDetalhado.setPreco(p);
        var expected = Arrays.asList(servicoDetalhado);
        var servicosDetalhados = Arrays.asList(ServicoDetalhadoMock.servicoDetalhado());

        //quando
        var actual = converter.toRepresentationList(servicosDetalhados);

        //entao
        assertEquals(expected, actual);
    }

}
