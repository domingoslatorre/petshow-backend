package hyve.petshow.service.implementation;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.VerificationToken;
import hyve.petshow.domain.enums.TipoConta;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.VerificationTokenRepository;
import hyve.petshow.service.port.AcessoService;

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
    @Autowired
    private VerificationTokenRepository tokenRepository;

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

    //TODO MELHORAR CODIGO DESTE MÉTODO
    @Override
    public Conta adicionarConta(Conta conta) throws BusinessException {
        var tipoConta = conta.getTipo();
        criptografarSenha(conta.getLogin());

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
    
    private Conta buscarConta(String email) throws Exception {
    	return buscarPorEmail(email).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

	@Override
	public Conta criaTokenVerificacao(Conta conta, String token) {
		var verificationToken = new VerificationToken(conta, token);
		tokenRepository.save(verificationToken);
		return conta;
	}

	@Override
	public VerificationToken buscarTokenVerificacao(String tokenVerificadcao) throws Exception {
		return tokenRepository.findByToken(tokenVerificadcao).orElseThrow(() -> new NotFoundException("Token informado não encontrado"));
	}

	@Override
	public Conta ativaConta(String token) throws Exception {
		var tokenVerificacao = buscarTokenVerificacao(token);		
		var conta = buscarConta(tokenVerificacao.getConta().getEmail());
		conta.setEnabled(true);
		return acessoRepository.save(conta);
		
	}
}
