package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Autonomo;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AutonomoRepository;
import hyve.petshow.service.port.AutonomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutonomoServiceImpl implements AutonomoService {
    @Autowired
    private AutonomoRepository repository;


    @Override
    public Autonomo salvaConta(Autonomo conta) throws Exception {
        validaNovaConta(conta);
        return repository.save(conta);
    }

    private void validaNovaConta(Autonomo conta) throws BusinessException {
        if (repository.findByEmail(conta.getLogin().getEmail()).isPresent()) {
            throw new BusinessException("Email já cadastrado no sistema");
        }

        if (repository.findByCpf(conta.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado no sistema");
        }
    }

    @Override
    public Autonomo obterContaPorId(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    public List<Autonomo> obterContas()  {
        return repository.findAll();
    }

    @Override
    public Autonomo obterPorLogin(Login login) throws Exception {
        return repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Login informado não encontrado no sistema"));
    }

    @Override
    public MensagemRepresentation removerConta(Long id) throws Exception {
        repository.deleteById(id);
        MensagemRepresentation mensagem = new MensagemRepresentation();
        mensagem.setId(id);
        mensagem.setSucesso(!repository.existsById(id));
        return mensagem;
    }

    @Override
    public Optional<Autonomo> buscaPorCpf(String cpf) { return repository.findByCpf(cpf); }

    @Override
    public Optional<Autonomo> buscaPorEmail(String email) { return repository.findByEmail(email); }

    @Override
    public Autonomo atualizaConta(Autonomo conta) { return repository.save(conta); }

    @Override
    public Autonomo atualizaConta(Long id, Autonomo conta) throws Exception {
        repository.findById(id).orElseThrow(()->new NotFoundException("Conta não encontrada"));
        return repository.save(conta);
    }
}
