package meecrowave.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Time {
    private long value;
    private String units;
}
