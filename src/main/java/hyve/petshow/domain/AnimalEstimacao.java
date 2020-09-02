package hyve.petshow.domain;

import lombok.Data;

@Data
public class AnimalEstimacao {
    private Long id;
    private String nome;
    private String foto;
    private Integer tipo;
}
