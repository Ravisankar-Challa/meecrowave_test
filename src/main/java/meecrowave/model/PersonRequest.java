package meecrowave.model;

import javax.validation.constraints.Pattern;

public class PersonRequest {
    
    @Pattern(regexp = "[A-Z]", message="Please check the name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
