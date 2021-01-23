package hyve.petshow.mock;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;

import java.util.Arrays;

import static hyve.petshow.mock.ContaMock.contaPrestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoRepresentationList;

public class PrestadorMock {
    public static Prestador prestador() {
        return new Prestador(contaPrestador(), Arrays.asList(servicoDetalhado()));
    }

    public static PrestadorRepresentation prestadorRepresentation() {
        var prestador = prestador();
        var prestadorRepresentation = new PrestadorRepresentation();

        prestadorRepresentation.setId(prestador.getId());
        prestadorRepresentation.setNome(prestador.getNome());
        prestadorRepresentation.setNomeSocial(prestador.getNomeSocial());
        prestadorRepresentation.setCpf(prestador.getCpf());
        prestadorRepresentation.setTelefone(prestador.getTelefone());
        prestadorRepresentation.setTipo(prestador.getTipo().getTipo());
        prestadorRepresentation.setFoto(prestador.getFoto());
        prestadorRepresentation.setMediaAvaliacao(prestador.getMediaAvaliacao());
        prestadorRepresentation.setEndereco(prestador.getEndereco());
        prestadorRepresentation.setLogin(prestador.getLogin());
        prestadorRepresentation.setGeolocalizacao(prestador.getGeolocalizacao());
        prestadorRepresentation.setServicos(servicoDetalhadoRepresentationList());

        return prestadorRepresentation;
    }
}