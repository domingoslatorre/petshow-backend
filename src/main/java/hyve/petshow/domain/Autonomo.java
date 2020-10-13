package hyve.petshow.domain;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
// @DiscriminatorValue(value="") //
public class Autonomo extends Prestador{
//    private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

//    private Autonomo(){
//    }

//    private Autonomo(String descricao,
//                     List<Pagamento> pagamentos){
//        super(descricao);
//        setPagamento(pagamentos);
//    }
}
