package hyve.petshow.controller.converter;

public interface Converter<T, Y> {
    Y toRepresentation(T domain);
    T toDomain( Y representation);
}
