package app.exceptions;

public class VisitorNotFoundException extends RuntimeException {
    public VisitorNotFoundException(Long id) {
        super(String.format("Посетитель с ID %d не найден", id));
    }
}
