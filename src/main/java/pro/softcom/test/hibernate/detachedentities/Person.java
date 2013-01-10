package pro.softcom.test.hibernate.detachedentities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Person {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;
    
    @OneToMany(mappedBy="person")
    private Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

}
