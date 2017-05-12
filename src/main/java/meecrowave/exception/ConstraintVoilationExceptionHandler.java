package meecrowave.exception;

import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import meecrowave.model.ErrorMessage;

@Provider
@ApplicationScoped
public class ConstraintVoilationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = Logger.getLogger(ConstraintVoilationExceptionHandler.class.getName());
    
    @Override
    public Response toResponse(ConstraintViolationException cve) {
        String errorString = cve.getConstraintViolations()
                                .stream()
                                .peek(this::logError)
                                .map(this::logError)
                                .collect(Collectors.joining(", "));
        
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(Status.BAD_REQUEST.getStatusCode());
        errorMessage.setErrorDescription(errorString);
        
        return Response.status(Status.BAD_REQUEST)
                        .entity(errorMessage)
                        .type("application/json")
                        .build();
    }
    
    public String logError(final ConstraintViolation<?> exception) {
        final String error = new StringJoiner(" : ")
                .add("Invalid value " + (exception.getInvalidValue() != null ? exception.getInvalidValue().toString() : ""))
                .add(exception.getRootBeanClass().getSimpleName())
                .add(exception.getPropertyPath().toString())
                .add(exception.getMessage())
                .toString();
        log.severe(error);
        return error;
    }
}
