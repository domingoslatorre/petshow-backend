package hyve.petshow.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoAnimalEstimacao {
    GATO(2),
    CACHORRO(12),
    REPTIL(22);

    private Integer id;
    private static Map map = new HashMap<>();

    TipoAnimalEstimacao(Integer id) {
        this.id = id;
    }

    static {
        for (TipoAnimalEstimacao tipoAnimalEstimacao : TipoAnimalEstimacao  .values()) {
            map.put(tipoAnimalEstimacao.id(), tipoAnimalEstimacao);
        }
    }

    public static TipoAnimalEstimacao valueOf(Integer tipoAnimalEstimacao) {
        return (TipoAnimalEstimacao) map.get(tipoAnimalEstimacao);
    }

    public Integer id(){
        return id;
    }
}
