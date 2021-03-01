package hyve.petshow.controller.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;
import java.util.stream.Collectors;

public interface Converter<DOMAIN, REPRESENTATION> {
    REPRESENTATION toRepresentation(DOMAIN domain);
    DOMAIN toDomain( REPRESENTATION representation);
    
    
    default List<REPRESENTATION> toRepresentationList(List<DOMAIN> domainList) {
    	return Optional.ofNullable(domainList).map(lista -> {
    		return lista.stream().map(el -> toRepresentation(el)).collect(Collectors.toList());
    	}).orElse(new ArrayList<REPRESENTATION>());
    }
    
    default List<DOMAIN> toDomainList(List<REPRESENTATION> representationList) {
    	return Optional.ofNullable(representationList).map(lista -> {
    		return lista.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    	}).orElse(new ArrayList<DOMAIN>());
    }
    
    default List<REPRESENTATION> toRepresentationList(Set<DOMAIN> domainList) {
    	return Optional.ofNullable(domainList).map(lista -> {
    		return lista.stream().map(el -> toRepresentation(el)).collect(Collectors.toList());
    	}).orElse(new ArrayList<REPRESENTATION>());
    }
    
    default Set<DOMAIN> toDomainSet(List<REPRESENTATION> representationList) {
    	return Optional.ofNullable(representationList).map(lista -> {
    		return lista.stream().map(el -> toDomain(el)).collect(Collectors.toSet());
    	}).orElse(Collections.emptySet());
    }

    default Page<REPRESENTATION> toRepresentationPage(Page<DOMAIN> domainPage){
        return new PageImpl<>(toRepresentationList(domainPage.getContent()), domainPage.getPageable(),
                domainPage.getTotalElements());
    }
}
