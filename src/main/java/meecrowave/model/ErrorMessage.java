package meecrowave.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private String errorDescription;
}
