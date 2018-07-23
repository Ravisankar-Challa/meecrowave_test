package meecrowave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    BAD_REQUEST("001", 400, "Request input parameters are incorrect"),
    SYSTEM_EXCEPTION("002", 500, "Unexpected system exception occurred"),
    DEPENDENCY_FAILURE("003", 500, "Down stream system or service unavailable"),
    INTERNAL_SERVER_ERROR("004", 500, "Internal server error occurred"),
    MEDIA_TYPE_NOT_SUPPORTED("005", 415, "HTTP media type not supported"),
    REQUESTED_METHOD_NOT_ALLOWED("006", 405,  "Requested http method not allowed"),
    JSON_PARSING_EXCEPTION("007", 500, "Failed to parse the json message"),
    URI_MISSING_EXCEPTION("008", 500, "URI must not be null when invoking"
            + " external service using rest template."),
    AUTHENTICATION_FAILED("009", 401, "Authentication failed"),
    REQUEST_TIMED_OUT("010", 500, "Down stream dependency failure"),
	UNSUCCESFUL_RESPONSE("011", 500, "Received error status between 300 to 520 from down stream");
    
    private final String errorCode;
    private final int httpStatus;
    private final String errorMessage;

}