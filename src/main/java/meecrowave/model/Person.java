package meecrowave.model;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.Pattern;

@JsonbPropertyOrder(value = {"personGender", "personAge"})
public class Person {
    
    @Pattern(regexp = "[A-Z]", message="Failed 1")
    private String personName;
    
    private int personAge;
    private String personGender;
    
    @JsonbDateFormat("MM/dd/yyyy")
    private LocalDate dateOfBirth;
    
    @Pattern(regexp = "[A-Z]", message="Failed 2")
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String name) {
        this.personName = name;
    }
    public int getPersonAge() {
        return personAge;
    }
    public void setPersonAge(int age) {
        this.personAge = age;
    }
    public String getPersonGender() {
        return personGender;
    }
    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
    
    
}
