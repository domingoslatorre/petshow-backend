package hyve.petshow.mock;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.enums.TipoAnimalEstimacao;

public class ServicoDetalhadoMock {
    public static ServicoDetalhado ServicoDetalhadoMock(){
    	ServicoDetalhado objeto = new ServicoDetalhado();

    	objeto.setId(1L);
    	
    	BigDecimal p = new BigDecimal(70);
    	objeto.setPreco(p);
    	
    	Servico s = new Servico(Long.valueOf(1),"Banho e Tosa", "Banhos quentinhos para o seu pet");
    	objeto.setTipo(s);
    	
    	//objeto.setServico("Banho e Tosa");
    	List <Integer> lista = new ArrayList<Integer>();
    	lista.add(TipoAnimalEstimacao.GATO.id());
    	lista.add(TipoAnimalEstimacao.CACHORRO.id());
    	objeto.setAnimaisAceitos(lista);

        return objeto;
    }

  
    public static ServicoDetalhadoRepresentation representation(){
    	ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();

    	BigDecimal p = new BigDecimal(70);
    	representation.setPreco(p);
//    	ServicoRepresentation s = new Servico(Long.valueOf(1),"Banho e Tosa", "Banhos quentinhos para o seu pet");
//    	representation.setTipo(s);
    	List <Integer> lista = new ArrayList<Integer>();
    	lista.add(TipoAnimalEstimacao.GATO.id());
    	lista.add(TipoAnimalEstimacao.CACHORRO.id());
    	representation.setAnimaisAceitos(lista);

        return representation;
    }

    public static MensagemRepresentation mensagem(){
        MensagemRepresentation mensagem = new MensagemRepresentation();

        mensagem.setId(1L);
        mensagem.setSucesso(Boolean.TRUE);
        mensagem.setMensagem("Operação executada com sucesso!");

        return mensagem;
    }

    public static MensagemRepresentation mensagem2(){
    	MensagemRepresentation mensagem = new MensagemRepresentation();

    	mensagem.setId(1L);
    	mensagem.setSucesso(Boolean.FALSE);
    	mensagem.setMensagem("Falha durante a execução da operação.");

        return mensagem;
    }
}
