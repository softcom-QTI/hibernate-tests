package pro.softcom.test.hibernate.detachedentities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PhoneNumber {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private NumberType type;

    private String number;

    @ManyToOne
    private Person person;
    
	public Long getId() {
		return id;
	}

    public NumberType getType() {
        return type;
    }

    public void setType(NumberType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
