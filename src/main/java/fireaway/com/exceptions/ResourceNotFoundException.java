package fireaway.com.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entity, Long id) {
        super(String.format("%s com ID %d não encontrado.", entity, id));
    }
}