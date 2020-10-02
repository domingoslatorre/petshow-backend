package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorConverterTest {
    private PrestadorConverter converter = new PrestadorConverter();

    @Test
    public void deve_retornar_prestador_convertido() {
        Prestador prestador = new Prestador();
        prestador.setId(1l);
//        Arraylist<ServicoDetelhado> servicosDetalhados = new ArrayList<~>();
//        ServicoDetelhado servicoDetelhado = new ServicoDetelhado;
//        servicosDetalhados.add(ServicoDetelhado);
//        prestador.setServicoDetelhado(servicosDetalhados);

        PrestadorRepresentation representation = converter.toRepresentation(prestador);

        assertEquals(prestador.getId(), representation.getId());
//        assertTrue(!representation.getServicoDetelhado().isEmpty());
    }

    @Test
    public void deve_retornar_representacao_convertida() {
        PrestadorRepresentation representation = new PrestadorRepresentation();
        representation.setId(1l);
//        List<ServicoDetelhadoRepresentation> servicosDetalhados = new ArrayList<ServicoDetelhadoRepresentation>();
//        ServicoDetelhadoRepresentation servicoDetelhado = new ServicoDetelhadoRepresentation();
//        servicoDetelhado.setServicoDetelhado(prestador);
//
//        servicosDetalhados.add(servicoDetelhado);
//
//        representation.setServicoDetelhado(servicosDetalhados);

        Prestador domain = converter.toDomain(representation);

        assertEquals(representation.getId(), domain.getId());
//        assertTrue(!domain.getServicoDetelhado().isEmpty());
    }
}
