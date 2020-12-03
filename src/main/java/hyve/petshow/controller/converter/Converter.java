package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Converter<T, Y> {
    Y toRepresentation(T domain);
    T toDomain( Y representation);
    
    
    default List<Y> toRepresentationList(List<T> domainList) {
    	return Optional.ofNullable(domainList).map(lista -> {
    		return lista.stream().map(el -> toRepresentation(el)).collect(Collectors.toList());
    	}).orElse(new ArrayList<Y>());
    }
    
    default List<T> toDomainList(List<Y> representationList) {
    	return Optional.ofNullable(representationList).map(lista -> {
    		return lista.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    	}).orElse(new ArrayList<T>());
    }
    
}
