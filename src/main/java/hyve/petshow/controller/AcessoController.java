package hyve.petshow.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AcessoService;
import hyve.petshow.util.JwtUtil;
import hyve.petshow.util.OnRegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/acesso")
public class AcessoController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AcessoService acessoService;
    @Autowired
    private ContaConverter contaConverter;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final String mensagemErro = "Erro durante a autenticação, usuário ou senha incorretos";

    @PostMapping("/login")
    public ResponseEntity<String> realizarLogin(@RequestBody Login login) throws Exception {
        try {
            realizarAutenticacao(login);
            var token = gerarToken(login.getEmail());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            log.error("{}, mensagem: {}, causa: {}", mensagemErro, e.getMessage(), e.getCause());
            throw new BusinessException(mensagemErro);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> realizarCadastro(@RequestBody ContaRepresentation contaRepresentation, HttpServletRequest request) throws BusinessException {
        try {
            verificarEmailExistente(contaRepresentation.getLogin().getEmail());
            var conta = adicionarConta(contaRepresentation);
            var token = gerarToken(conta);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(conta, request.getLocale(), appUrl));
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            log.error("{}, mensagem: {}, causa: {}", mensagemErro, e.getMessage(), e.getCause());
            throw new BusinessException(mensagemErro);
        }
    }

    private void realizarAutenticacao(Login login) throws AuthenticationException{
        var token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        authenticationManager.authenticate(token);
    }

    private String gerarToken(String email) throws Exception {
        var conta = acessoService.buscarPorEmail(email)
                .orElseThrow(() -> new NotFoundException("Login informado não encontrado no sistema"));
        if(!conta.getEnabled()) {
        	throw new BusinessException("Conta informada ainda não foi ativada");
        }
        var token = jwtUtil.generateToken(email, conta.getId(), conta.getTipo());
        return token;
    }

    private String gerarToken(Conta conta) {
        var token = jwtUtil.generateToken(conta.getLogin().getEmail(), conta.getId(), conta.getTipo());
        return token;
    }

    private void verificarEmailExistente(String email) throws BusinessException {
        if(acessoService.buscarPorEmail(email).isPresent()){
            throw new BusinessException("Email já cadastrado no sistema");
        }
    }

    private Conta adicionarConta(ContaRepresentation contaRepresentation) throws BusinessException {
        var request = contaConverter.toDomain(contaRepresentation);
        var conta = acessoService.adicionarConta(request);
        return conta;
    }
    
    @GetMapping("/ativar")
    public ResponseEntity<String> confirmarRegistro(@RequestBody String tokenVerificadcao) throws Exception {
		var conta = acessoService.ativaConta(tokenVerificadcao);
    	var tokenRetorno = gerarToken(conta.getEmail());
    	return ResponseEntity.ok(tokenRetorno);
    }
    
}
