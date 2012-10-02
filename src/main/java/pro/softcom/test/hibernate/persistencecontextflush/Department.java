package pro.softcom.test.hibernate.persistencecontextflush;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name=Department.SELECT_ALL_DEPARTMENTS, query= "select d from Department d left join fetch d.employees")
public class Department {

    public static final String SELECT_ALL_DEPARTMENTS = "selectAllDepartments";
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy="department")
    private Set<Employee> employees = new HashSet<Employee>();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

}
