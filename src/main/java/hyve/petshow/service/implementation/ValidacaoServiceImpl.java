package hyve.petshow.service.implementation;

import hyve.petshow.domain.Validacao;
import hyve.petshow.repository.ValidacaoRepository;
import hyve.petshow.service.port.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ValidacaoServiceImpl implements ValidacaoService {
    @Autowired
    private ValidacaoRepository validacaoRepository;

    @Override
    public List<Validacao> obterValidacoes() {
        List<Validacao> validacoes = new ArrayList<>();

        validacaoRepository.findAll().forEach(validacao -> validacoes.add(validacao));

        return validacoes;
    }

    @Override
    public Validacao obterValidacao(Long id) {
        Validacao response = new Validacao();
        Optional<Validacao> validacao = validacaoRepository.findById(id);

        if(validacao.isPresent())
            response = validacao.get();

        return response;
    }
}
