package hyve.petshow.mock;

import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.domain.enums.TipoConta;

import static hyve.petshow.mock.AuditoriaMock.auditoria;
import static hyve.petshow.mock.LoginMock.login;
import static hyve.petshow.util.AuditoriaUtils.ATIVO;

public class ContaMock {
    public static Conta contaCliente(){
        Conta conta = new Conta();

        conta.setId(1L);
        conta.setNome("Teste");
        conta.setNomeSocial("teste");
        conta.setCpf("544444444");
        conta.setTelefone("1129299292");
        conta.setTipo(TipoConta.CLIENTE);
        conta.setFoto("");
        conta.setEndereco(new Endereco());
        conta.setLogin(login());
        conta.setGeolocalizacao(new Geolocalizacao());
        conta.setMediaAvaliacao(1.5F);
        conta.setAuditoria(auditoria(ATIVO));

        return conta;
    }

    public static Conta contaPrestador(){
        Conta conta = new Conta();

        conta.setId(1L);
        conta.setNome("Teste");
        conta.setNomeSocial("teste");
        conta.setCpf("544444444");
        conta.setTelefone("1129299292");
        conta.setTipo(TipoConta.PRESTADOR_AUTONOMO);
        conta.setFoto("");
        conta.setEndereco(new Endereco());
        conta.setLogin(new Login());
        conta.setGeolocalizacao(new Geolocalizacao());
        conta.setMediaAvaliacao(1.5F);
        conta.setAuditoria(auditoria("S"));

        return conta;
    }

    public static ContaRepresentation contaRepresentation(){
        ContaRepresentation contaRepresentation = new ContaRepresentation();

        contaRepresentation.setId(1L);
        contaRepresentation.setNome("Teste");
        contaRepresentation.setNomeSocial("teste");
        contaRepresentation.setCpf("544444444");
        contaRepresentation.setTelefone("1129299292");
        contaRepresentation.setTipo(TipoConta.CLIENTE.getTipo());
        contaRepresentation.setFoto("");
        contaRepresentation.setEndereco(new Endereco());
        contaRepresentation.setLogin(new Login());
        contaRepresentation.setGeolocalizacao(new Geolocalizacao());
        contaRepresentation.setMediaAvaliacao(1.5F);

        return contaRepresentation;
    }
}
