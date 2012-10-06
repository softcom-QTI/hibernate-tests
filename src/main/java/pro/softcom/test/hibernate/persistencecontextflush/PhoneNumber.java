package pro.softcom.test.hibernate.persistencecontextflush;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PhoneNumber {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private NumberType type;

    private String number;

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

}
