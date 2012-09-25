package pro.softcom.test.hibernate.mapkeybyentity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Company {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="company")
    @MapKeyJoinColumn(name="DEPT_ID")
    private Map<Department, Employee> departmentResponsibles = new HashMap<Department, Employee>();

	public Long getId() {
		return id;
	}

	public Map<Department, Employee> getDepartmentResponsibles() {
		return departmentResponsibles;
	}

}
