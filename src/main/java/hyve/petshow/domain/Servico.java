package hyve.petshow.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Entity(name = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String grupo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipo")
    private List<ServicoDetalhado> servicosDetalhados = new ArrayList<>();
}