package org.wds.taskmanager.exception;

public class NotFoundException extends TaskException {

    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
