package pro.softcom.test.hibernate.extendedpersistencecontext;


import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ExtendedPersistenceContextTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Department.class.getPackage())
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@EJB
	DepartmentManager departmentManager;
	
	/**
     * Company has a map of entities (Employee) keyed by entity (Department)
	 */
	@Test
	@UsingDataSet("datasets/extendedpersistencecontext/initial.yml")
	public void testPersist() throws Exception {

		
		Department dept = em.find(Department.class, 1L);
		Employee emp = em.find(Employee.class, 1L);

		assertEquals((Long) 1L, dept.getId());
}

}
