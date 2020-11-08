package hyve.petshow.controller;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AcessoService;
import hyve.petshow.util.JwtUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/acesso")
@OpenAPIDefinition(info = @Info(title = "API de acesso à aplicação",
        description = "API utilizada para a realização de login e cadastro"))
public class AcessoController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AcessoService acessoService;
    @Autowired
    private ContaConverter contaConverter;

    private final String mensagemErro = "Erro durante a autenticação, usuário ou senha incorretos";

    @Operation(summary = "Realiza o login, gerando um token para ser utilizado nas demais APIs.")
    @PostMapping("/login")
    public ResponseEntity<String> realizarLogin(
            @Parameter(description = "Objeto utilizado para realizar o login.")
            @RequestBody Login login) throws NotFoundException, BusinessException {
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

    @Operation(summary = "Realiza o cadastro, salvando o usuário no sistema e " +
            "gerando um token para ser utilizado nas demais APIs.")
    @PostMapping("/cadastro")
    public ResponseEntity<String> realizarCadastro(
            @Parameter(description = "Objeto da conta que será cadastrada.")
            @RequestBody ContaRepresentation contaRepresentation) throws BusinessException {
        verificarEmailExistente(contaRepresentation.getLogin().getEmail());
        var conta = adicionarConta(contaRepresentation);
        var token = gerarToken(conta);

        return ResponseEntity.ok(token);
    }

    private void realizarAutenticacao(Login login) throws AuthenticationException{
        var token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        authenticationManager.authenticate(token);
    }

    private String gerarToken(String email) throws NotFoundException {
        var conta = acessoService.buscarPorEmail(email)
                .orElseThrow(() -> new NotFoundException("Login informado não encontrado no sistema"));

        return jwtUtils.generateToken(email, conta.getId(), conta.getTipo());
    }

    private String gerarToken(Conta conta) {
        return jwtUtils.generateToken(conta.getLogin().getEmail(), conta.getId(), conta.getTipo());
    }

    private void verificarEmailExistente(String email) throws BusinessException {
        if(acessoService.buscarPorEmail(email).isPresent()){
            throw new BusinessException("Email já cadastrado no sistema");
        }
    }

    private Conta adicionarConta(ContaRepresentation contaRepresentation) throws BusinessException {
        var request = contaConverter.toDomain(contaRepresentation);

        return acessoService.adicionarConta(request);
    }
}
