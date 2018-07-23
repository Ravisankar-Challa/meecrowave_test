package meecrowave.exception;

@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    public ApplicationException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }    
}
