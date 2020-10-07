package hyve.petshow.service.implementation;

import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;

@Service
public class AcessoServiceImpl implements AcessoService {
    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var cliente = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
        var login = cliente.getLogin();

        return new User(login.getEmail(), login.getSenha(), new ArrayList<>());
    }
}
