package org.wds.taskmanager.exception;

public class RepositoryException extends TaskException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
