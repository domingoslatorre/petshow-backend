package hyve.petshow.service.implementation;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.TipoConta;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.service.port.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

@Service
public class AcessoServiceImpl implements AcessoService {
    @Autowired
    private AcessoRepository acessoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PrestadorRepository prestadorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var conta = acessoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada"));
        var login = conta.getLogin();
        var user = new User(login.getEmail(), login.getSenha(), new ArrayList<>());
        return user;
    }

    @Override
    public Optional<Conta> buscarPorEmail(String email) {
        var conta = acessoRepository.findByEmail(email);
        return conta;
    }

    @Override
    public Conta adicionarConta(Conta conta) throws BusinessException {
        var tipoConta = conta.getTipo();
        criptografarSenha(conta.getLogin());

        conta.setAuditoria(geraAuditoriaInsercao(Optional.empty()));

        if(tipoConta.equals(TipoConta.CLIENTE)){
            var cliente = new Cliente(conta);
            conta = clienteRepository.save(cliente);
        } else if(tipoConta.equals(TipoConta.PRESTADOR_AUTONOMO)){
            var prestador = new Prestador(conta);
            conta = prestadorRepository.save(prestador);
        } else {
            throw new BusinessException("Tipo de cliente inexistente");
        }

        return conta;
    }

    private void criptografarSenha(Login login){
        var senha = login.getSenha();
        login.setSenha(passwordEncoder.encode(senha));
    }
}
