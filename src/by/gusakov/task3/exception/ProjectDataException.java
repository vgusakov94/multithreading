package by.gusakov.task3.exception;

public class ProjectDataException extends Exception {
    public ProjectDataException() {
    }

    public ProjectDataException(String message) {
        super(message);
    }

    public ProjectDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectDataException(Throwable cause) {
        super(cause);
    }
}
