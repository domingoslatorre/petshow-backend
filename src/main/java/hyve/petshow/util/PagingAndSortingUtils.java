package hyve.petshow.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingAndSortingUtils {
    public static Pageable geraPageable(Integer pagina, Integer quantidadeItens){
        return PageRequest.of(pagina, quantidadeItens);
    }

    public static Pageable geraPageableOrdemMaisNovo(Integer pagina, Integer quantidadeItens){
        return PageRequest.of(pagina, quantidadeItens, Sort.by("auditoria.dataCriacao").descending());
    }
}
