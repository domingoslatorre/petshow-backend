package hyve.petshow.service.implementation;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Prestador;
//import hyve.petshow.controller.representation.ServicoDetalhadoResponseRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;

@Service
public class ServicoDetalhadoServiceImpl implements ServicoDetalhadoService {

    private static final String MENSAGEM_SUCESSO = "Operação executada com sucesso!";
    private static final String MENSAGEM_FALHA = "Falha durante a execução da operação.";

    @Autowired
    private ServicoDetalhadoRepository repository;

    //criarServicos
    @Override
    public ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado) {
        return repository.save(servicoDetalhado);
    }
    

//    @Override
//    public List<ServicoDetalhado> buscarServicosDetalhadosPorPrestador(Long id) {
//        return repository.findByPrestador(id);
//    }

    //
    @Override
    public ServicoDetalhado atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest) throws Exception {
        repository.findById(id).orElseThrow(() -> new NotFoundException("Serviço não encontrado"));
        return repository.save(servicoDetalhadoRequest);
    }

    @Override
    public MensagemRepresentation removerServicoDetalhado(Long id) {
    	repository.deleteById(id);
		MensagemRepresentation mensagem = new MensagemRepresentation();
		mensagem.setId(id);
		mensagem.setSucesso(!repository.existsById(id));
		return mensagem;
    }
    
  @Override
  public List<Prestador> buscarPrestadoresPorServico(String nome) {
      return repository.findByTipoContainingIgnoreCase(nome);
  }

    
}

