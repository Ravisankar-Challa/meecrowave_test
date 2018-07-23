package meecrowave.exception;

@SuppressWarnings("serial")
public class RequestTimeOutException extends RuntimeException {
    
    public RequestTimeOutException(final Throwable exp) {
        super(exp);
    }
}
