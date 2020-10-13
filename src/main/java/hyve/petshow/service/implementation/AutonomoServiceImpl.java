package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Autonomo;
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
    public Autonomo buscarPorId(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    public List<Autonomo> buscarContas()  {
        return repository.findAll();
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
    public Optional<Autonomo> buscarPorEmail(String email) { return repository.findByEmail(email); }

    @Override
    public Autonomo atualizarConta(Long id, Autonomo conta) throws Exception {
        repository.findById(id).orElseThrow(()->new NotFoundException("Conta não encontrada"));
        return repository.save(conta);
    }
}
