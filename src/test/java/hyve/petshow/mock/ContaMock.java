package hyve.petshow.mock;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;

public class ContaMock {
    public static Conta conta(){
        Conta conta = new Conta();

        conta.setId(1L);
        conta.setNome("Teste");
        conta.setNomeSocial("teste");
        conta.setCpf("544444444");
        conta.setTelefone("1129299292");
        conta.setTipo(TipoConta.CLIENTE);
        conta.setFoto("");
        conta.setEndereco(new Endereco());
        conta.setLogin(new Login());

        return conta;
    }
}
