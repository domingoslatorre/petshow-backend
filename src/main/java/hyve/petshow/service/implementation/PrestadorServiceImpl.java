package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import hyve.petshow.domain.Prestador;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.service.port.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;

@Service
public class PrestadorServiceImpl implements PrestadorService {
    @Autowired
    private PrestadorRepository repository;

    @Override
    public Prestador salvaConta(Prestador conta) throws Exception {
        validaNovaConta(conta);
        return repository.save(conta);
    }

    private void validaNovaConta(Prestador conta) throws BusinessException {
        if (repository.findByEmail(conta.getLogin().getEmail()).isPresent()) {
            throw new BusinessException("Email já cadastrado no sistema");
        }

        if (repository.findByCpf(conta.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado no sistema");
        }
    }

    @Override
    public Prestador obterContaPorId(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    public List<Prestador> obterContas() {
        return repository.findAll();
    }

    @Override
    public Prestador obterPorLogin(Login login) throws Exception {
        return repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Login informado não encontrado no sistema"));
    }

    @Override
    public MensagemRepresentation removerConta(Long id) {
        repository.deleteById(id);
        MensagemRepresentation mensagem = new MensagemRepresentation();
        mensagem.setId(id);
        mensagem.setSucesso(!repository.existsById(id));
        return mensagem;
    }

    @Override
    public Optional<Prestador> buscaPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public Optional<Prestador> buscaPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Prestador atualizaConta(Prestador conta) {
        return repository.save(conta);
    }

}
