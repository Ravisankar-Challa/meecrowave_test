package meecrowave.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import meecrowave.model.ErrorMessage;

@Provider
@ApplicationScoped
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException>{

    @Override
    public Response toResponse(ApplicationException exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode("000");
        errorMessage.setErrorDescription(exception.getErrorMessage());
        
        return Response.status(exception.getErrorCode())
                        .entity(errorMessage)
                        .type("application/json")
                        .build();
    }
    
}
