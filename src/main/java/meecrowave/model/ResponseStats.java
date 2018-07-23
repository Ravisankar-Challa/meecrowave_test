package meecrowave.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseStats {

    private String httpMethod;
    private String uri;
    private int status;
    private Time time;
    private String error;

}
