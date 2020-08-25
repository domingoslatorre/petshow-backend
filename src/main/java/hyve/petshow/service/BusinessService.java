package hyve.petshow.service;

import java.util.Collections;
import java.util.Optional;

public interface BusinessService<T, ID> {
    T criar(T domainObject);
    T atualizar(T domainObject);
    Boolean remover(ID id);

    default Optional<T> obterUmPorId(ID id){
        return Optional.empty();
    }

    default Iterable<T> obterTodos(){
        return Collections.emptyList();
    }
}
