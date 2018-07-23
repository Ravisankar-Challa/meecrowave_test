package meecrowave.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessLog {
    private String srcIp;
    private String xForwardedFor;
    private String httpMethod;
    private String uri;
    private String protocol;
    private Integer status;
    private Long responseTime;
    private String userAgent;
    private String contentType;
    private String referer;
    private String endUserIp;
    private String geoLocation;
}
