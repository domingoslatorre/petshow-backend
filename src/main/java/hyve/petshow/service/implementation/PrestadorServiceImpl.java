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
    private final String CONTA_NAO_ENCONTRADA = "Conta não encontrada";

    @Autowired
    private PrestadorRepository repository;

    @Override
    public Prestador buscarPorId(Long id) throws Exception {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(CONTA_NAO_ENCONTRADA));
    }

    @Override
    public Prestador atualizarConta(Long id, Prestador request) throws Exception {
        var prestador = repository.findById(id)
                .orElseThrow(()->new NotFoundException(CONTA_NAO_ENCONTRADA));

        prestador.setTelefone(request.getTelefone());
        prestador.setEndereco(request.getEndereco());

        return repository.save(prestador);
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
    public Optional<Prestador> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }
}
