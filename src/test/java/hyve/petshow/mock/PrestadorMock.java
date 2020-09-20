package hyve.petshow.mock;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PrestadorMock {
    public static List<Prestador> dbMock = new ArrayList<Prestador>(Arrays.asList(new Prestador(1l, "Teste", "Teste", "44444444444", "1129292828", TipoConta.CLIENTE, "", new Endereco(), new Login(), new ArrayList<servicosDetalhados>())));

    public static List<Prestador> obterContas() {
        return dbMock;
    }

    public static Prestador salvaConta(Prestador conta) {
        conta.setId((long) dbMock.size() + 1);
        dbMock.add(conta);
        return conta;
    }


    public static Optional<Prestador> buscaPorId(Long id) {
        return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
    }


    public static Optional<Prestador> buscaPorLogin(Login login) {
        return dbMock.stream().filter(el -> el.getLogin().equals(login)).findFirst();
    }




    private static Prestador salvaCliente(Prestador cliente) {
        Prestador buscaPorId = (Prestador) buscaPorId(cliente.getId()).get();
        buscaPorId.setServicoDetalhado(cliente.getServicoDetalhado());
        return buscaPorId;
    }


    public static void removeConta(Conta conta) {
        dbMock.remove(conta);
    }


    public static void removePorId(Long id) {
        dbMock = dbMock.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
    }

    public static Conta atualizaConta(Conta conta) {
        Conta contaDb = buscaPorId(conta.getId()).get();
        contaDb.setCpf(conta.getCpf());
        contaDb.setEndereco(conta.getEndereco());
        contaDb.setFoto(conta.getFoto());
        contaDb.setLogin(conta.getLogin());
        contaDb.setNome(conta.getNome());
        contaDb.setNomeSocial(conta.getNomeSocial());
        contaDb.setTelefone(conta.getTelefone());
        contaDb.setTipo(conta.getTipo());
        if(conta instanceof Cliente) {
            salvaCliente((Cliente) conta);
        }

        return contaDb;

    }

    public static Optional<Cliente> buscarPorEmail(String email) {
        return dbMock.stream()
                .filter(el -> email.equals(el.getLogin().getEmail()))
                .findFirst();
    }

    public static Optional<Cliente> buscaPorCpf(String cpf) {
        return dbMock.stream()
                .filter(el -> cpf.equals(el.getCpf()))
                .findFirst();
    }
}