package hyve.petshow.mock;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;

import java.util.Arrays;

import static hyve.petshow.mock.ContaMock.contaPrestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;

public class PrestadorMock {
    public static Prestador prestador() {
        return new Prestador(contaPrestador(), Arrays.asList(servicoDetalhado()));
    }

    public static PrestadorRepresentation prestadorRepresentation() {
        var converter = new PrestadorConverter();

        return converter.toRepresentation(prestador());
    }
}