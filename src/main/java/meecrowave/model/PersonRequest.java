package meecrowave.model;

import javax.validation.constraints.Pattern;

public class PersonRequest {
    
    @Pattern(regexp = "[A-Z]", message="Failed 3")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
