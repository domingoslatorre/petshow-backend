package hyve.petshow.controller;

import hyve.petshow.domain.Login;
import hyve.petshow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acesso")
public class AcessoController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody Login login){
        //TODO TRATAR EXCECAO CORRETAMENTE
        try {
            var token = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());

            authenticationManager.authenticate(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return jwtUtil.generateToken(login.getEmail());
    }
}
