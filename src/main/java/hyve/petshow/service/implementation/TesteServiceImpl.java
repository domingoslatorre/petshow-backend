package hyve.petshow.service.implementation;

import hyve.petshow.domain.Teste;
import hyve.petshow.repository.TesteRepository;
import hyve.petshow.service.port.TesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TesteServiceImpl implements TesteService {
    @Autowired
    private TesteRepository testeRepository;

    @Autowired
    private Teste teste;

    @Override
    public List<Teste> obterTestes() {
        List<Teste> testes = new ArrayList<>();

        testeRepository.findAll().forEach(teste -> testes.add(teste));

        return testes;
    }

    @Override
    public Teste obterTeste(Long id) {
        Optional<Teste> testeFound = testeRepository.findById(id);

        if(testeFound.isPresent()){
            teste.setId(testeFound.get().getId());
            teste.setTeste(testeFound.get().getTeste());
        }

        return teste;
    }
}
