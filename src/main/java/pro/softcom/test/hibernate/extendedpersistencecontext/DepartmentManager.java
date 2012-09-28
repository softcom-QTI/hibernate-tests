package pro.softcom.test.hibernate.extendedpersistencecontext;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
public class DepartmentManager {
    
	@PersistenceContext(unitName="primary", type = PersistenceContextType.EXTENDED)
	EntityManager em;
	
	@Resource
	SessionContext ctx;
	
	Department dept;

	public void init(long deptId) {
		dept = em.find(Department.class, deptId);
	}

	public void setName(String name, boolean rollback) {
		dept.setName(name);
		if (rollback) {
		    ctx.setRollbackOnly();
		}
	}

	public void addEmployee(long empId) {
		Employee emp = em.find(Employee.class, empId);
		dept.getEmployees().add(emp);
		emp.setDepartment(dept);
	}

	public int getEmployeeCount() {
	    return dept.getEmployees().size();
	}
	
	@Remove
	public void finished() {
	}
}
