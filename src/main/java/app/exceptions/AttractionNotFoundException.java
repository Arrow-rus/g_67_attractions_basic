package app.exceptions;

public class AttractionNotFoundException extends RuntimeException {
    public AttractionNotFoundException(Long id) {
        super(String.format("Аттракцион с ID %d не найден", id));
    }
}
