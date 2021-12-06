package app.data_ingestion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource already exists.")
public class ResourceAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 2L;

    public ResourceAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
