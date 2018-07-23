package meecrowave.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement	
public class Member implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GEN_NAME")
	@SequenceGenerator(name="GEN_NAME", allocationSize=1, initialValue=1, sequenceName="MEM_SEQ_GEN")
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}