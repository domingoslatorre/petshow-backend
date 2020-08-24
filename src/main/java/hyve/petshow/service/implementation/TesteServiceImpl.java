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

    @Override
    public List<Teste> obterTestes() {
        List<Teste> testes = new ArrayList<>();

        testeRepository.findAll().forEach(teste -> testes.add(teste));

        return testes;
    }

    @Override
    public Teste obterTeste(Long id) {
        Teste response = new Teste();
        Optional<Teste> teste = testeRepository.findById(id);

        if(teste.isPresent())
            response = teste.get();

        return response;
    }
}
