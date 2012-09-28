package pro.softcom.test.hibernate.mapkeybyentity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	@JoinTable(name = "DEPT_EMP_RESP", joinColumns = @JoinColumn(name = "COMP_ID"), inverseJoinColumns = @JoinColumn(name = "EMP_ID"))
	@MapKeyJoinColumn(name = "DEPT_ID", nullable = true)
	private Map<Department, Employee> departmentResponsibles = new HashMap<Department, Employee>();

	private String name;

	public Long getId() {
		return id;
	}
	
	public Map<Department, Employee> getDepartmentResponsibles() {
		return departmentResponsibles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
