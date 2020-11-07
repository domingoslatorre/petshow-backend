package hyve.petshow.mock;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;

import java.util.Arrays;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static hyve.petshow.mock.ContaMock.contaCliente;
import static hyve.petshow.mock.ContaMock.contaPrestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;

public class PrestadorMock {
    public static Prestador prestador() {
        var prestador = new Prestador(contaPrestador(), Arrays.asList(servicoDetalhado()));

        return prestador;
    }
}