package meecrowave.exception;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import meecrowave.model.ErrorMessage;

@Provider
@ApplicationScoped
public class UnsuccesfulResponseExceptionHandler implements ExceptionMapper<UnsuccesfulResponseException> {

	@Override
	public Response toResponse(UnsuccesfulResponseException exception) {
		ErrorCodes unsuccesfulResponse = ErrorCodes.UNSUCCESFUL_RESPONSE;
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(unsuccesfulResponse.getErrorCode());
		errorMessage.setErrorName(unsuccesfulResponse.name());
		errorMessage.setErrorMessage(unsuccesfulResponse.getErrorMessage());
		errorMessage.setErrorDescription(exception.getMessage());

		return Response.status(unsuccesfulResponse.getHttpStatus())
					   .entity(errorMessage)
					   .type(MediaType.APPLICATION_JSON)
					   .build();
	}

}
