package hyve.petshow.domain;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;

import javax.persistence.*;

@Data //gera getters and setters e Hashcode
@Entity //nao temos uma entidade do tipo Prestador no MER
// @DiscriminatorValue(value="") //
public class Prestador extends Conta{
//    @OneToMany//(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)//Lazy para tipo lista no resto deixa Igger
//    @JoinColumn(name="fk_conta")
    //private List<ServicoDetalhado> servicosDetalhados = new ArrayList<ServicoDetalhado>();
    private String descricao;

    public Prestador() {
    }

    public Prestador(Long id,
                         String nome,
                         String nomeSocial,
                         String cpf,
                         String telefone,
                         TipoConta tipo,
                         String foto,
                         Endereco endereco,
                         Login login,
                        String descricao
//                       List<ServicoDetalhado> servicosDetalhados
                       )
            {
            super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
            setDescricao(descricao);
//          setServicoDetalhado(servicosDetalhados);
        }



}
