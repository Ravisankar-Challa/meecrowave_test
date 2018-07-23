package meecrowave.exception;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class UnsuccesfulResponseException extends RuntimeException {

    private final int httpStatus;
    
    public UnsuccesfulResponseException(final String exp, final int httpStatus) {
        super(exp);
        this.httpStatus = httpStatus;
    }
}
