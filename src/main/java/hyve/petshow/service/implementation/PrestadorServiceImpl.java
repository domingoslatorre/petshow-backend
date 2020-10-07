package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.service.port.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestadorServiceImpl implements PrestadorService {
    @Autowired
    private PrestadorRepository repository;

    @Override
    public Prestador buscarPorId(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    public List<Prestador> buscarContas() {
        return repository.findAll();
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
    public Optional<Prestador> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public Optional<Prestador> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Prestador atualizarConta(Long id, Prestador conta) throws Exception {
        repository.findById(id).orElseThrow(()->new NotFoundException("Conta não encontrada"));
        return repository.save(conta);
    }

}
