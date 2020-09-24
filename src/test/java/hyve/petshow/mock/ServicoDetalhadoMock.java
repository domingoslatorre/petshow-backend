package hyve.petshow.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
//import hyve.petshow.domain.enums.TipoAnimalEstimacao;

public class ServicoDetalhadoMock {
		public static ServicoDetalhado criarServicoDetalhado() {
		ServicoDetalhado sd = new ServicoDetalhado();
    	sd.setId(1l);
    	
    	BigDecimal p = new BigDecimal(70);
    	sd.setPreco(p);
    	
    	Servico s = new Servico();
    	s.setId(Long.valueOf(1));
    	s.setNome("Banho e Tosa");
    	s.setDescricao("Banhos quentinhos para o seu pet");
    	
    	sd.setTipo(s);
    	return sd;
		}
    	
    	public static List<ServicoDetalhado> dbMock = new ArrayList<ServicoDetalhado>(Arrays.asList(criarServicoDetalhado()));
    	
    	public static List<ServicoDetalhado> findAll() {
    		return dbMock;
    	}

//    	public static Optional<ServicoDetalhado> buscarPorPrestador(Long id) {
//    		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
//    	}
    	
    	
    	public static List<ServicoDetalhado> saveAll(List<ServicoDetalhado> servicosDetalhados) {
    		servicosDetalhados.add(criarServicoDetalhado());
    		dbMock = servicosDetalhados;		
    		return dbMock;
    	}
    	
    	public static Optional<ServicoDetalhado> findById(Long id) {
    		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
    	}
    	
    	public static ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado ServicoDetalhado) {
    		ServicoDetalhado buscaPorId = (ServicoDetalhado) findById(ServicoDetalhado.getId()).get();
    		return buscaPorId;
    	}
    	
    	
    	public static void removerPorId(Long id) {
    		dbMock = dbMock.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
    	}

    	public static ServicoDetalhado atualizarServicoDetalhado(ServicoDetalhado servicoDetalhado) {
    		ServicoDetalhado servicoDetalhadoDb = findById(servicoDetalhado.getId()).get();
    		servicoDetalhadoDb.setPreco(servicoDetalhado.getPreco());
    		servicoDetalhadoDb.setTipo(servicoDetalhado.getTipo());
    		if(servicoDetalhado instanceof ServicoDetalhado) {
    			adicionarServicoDetalhado((ServicoDetalhado) servicoDetalhado);
    		}
    		
    		return servicoDetalhadoDb;
    		
    	}

    	    	
    	//ANTIGO:
    	
    	
//    	ServicoDetalhado objeto = new ServicoDetalhado();
//
//    	objeto.setId(1L);
//    	BigDecimal p = new BigDecimal(70);
//    	objeto.setPreco(p);
//    	
//    	
//    	Servico s = new Servico();
//    	s.setId(Long.valueOf(1));
//    	s.setNome("Banho e Tosa");
//    	s.setDescricao("Banhos quentinhos para o seu pet");
//    	objeto.setTipo(s);
//    	
//    	//objeto.setServico("Banho e Tosa");
////    	List <TipoAnimalEstimacao> lista = new ArrayList<>();
////    	lista.add(TipoAnimalEstimacao.GATO);
////    	lista.add(TipoAnimalEstimacao.CACHORRO);
//////    	objeto.setAnimaisAceitos(lista);
//
//        return objeto;
//    }
//    
//    public static ServicoDetalhado servicoDetalhadoAlt(){
//    	ServicoDetalhado objeto = new ServicoDetalhado();
//
//    	objeto.setId(1L);
//    	
//    	BigDecimal p = new BigDecimal(80);
//    	objeto.setPreco(p);
//    	
//    	Servico s = new Servico();
//    	s.setId(Long.valueOf(1));
//    	s.setNome("Banho e Tosa");
//    	s.setDescricao("Banhos quentinhos para o seu pet");
//    	objeto.setTipo(s);
//    	
////    	//objeto.setServico("Banho e Tosa");
////    	List <TipoAnimalEstimacao> lista = new ArrayList<>();
////    	lista.add(TipoAnimalEstimacao.REPTIL);
////    	lista.add(TipoAnimalEstimacao.AVE);
//////    	objeto.setAnimaisAceitos(lista);
//
//        return objeto;
//    }
//    
//    
//  
//    public static ServicoDetalhadoRepresentation representation(){
//    	ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();
//
//    	BigDecimal p = new BigDecimal(70);
//    	representation.setPreco(p);
//    	ServicoRepresentation s = new ServicoRepresentation();
////    	s.setId(Long.valueOf(1));
//    	s.setNome("Banho e Tosa");
//    	s.setDescricao("Banhos quentinhos para o seu pet");
//    	representation.setTipo(s);
//    	
////    	List <TipoAnimalEstimacao> lista = new ArrayList<TipoAnimalEstimacao>();
////    	lista.add(TipoAnimalEstimacao.GATO);
////    	lista.add(TipoAnimalEstimacao.CACHORRO);
//////    	representation.setAnimaisAceitos(lista);
//
//        return representation;
//    }
//
//    public static MensagemRepresentation mensagem(){
//        MensagemRepresentation mensagem = new MensagemRepresentation();
//
//        mensagem.setId(1L);
//        mensagem.setSucesso(Boolean.TRUE);
//        mensagem.setMensagem("Opera��o executada com sucesso!");
//
//        return mensagem;
//    }
//
//    public static MensagemRepresentation mensagem2(){
//    	MensagemRepresentation mensagem = new MensagemRepresentation();
//
//    	mensagem.setId(1L);
//    	mensagem.setSucesso(Boolean.FALSE);
//    	mensagem.setMensagem("Falha durante a execu��o da opera��o.");
//
//        return mensagem;
//    }
}
