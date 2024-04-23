package moreno.joaquin.filemanagerapp.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String username) {

        super(String.format("%s already exists", username));
    }
}
