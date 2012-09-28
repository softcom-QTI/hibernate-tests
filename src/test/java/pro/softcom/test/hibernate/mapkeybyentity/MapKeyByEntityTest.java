package pro.softcom.test.hibernate.mapkeybyentity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Transactional
@Cleanup(phase = TestExecutionPhase.NONE)
public class MapKeyByEntityTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Company.class.getPackage())
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	/**
     * Company has a map of entities (Employee) keyed by entity (Department)
	 */
	@Test
	@UsingDataSet("mapkeybyentity/initial.yml")
	@ShouldMatchDataSet("mapkeybyentity/expected.yml")
	public void testPersist() throws Exception {

		// Given
		Company comp = em.find(Company.class, 1L);
		Department dept = em.find(Department.class, 1L);
		Employee emp = em.find(Employee.class, 1L);
		assertFalse("Map does not contain department responsible", comp.getDepartmentResponsibles().containsKey(dept));
		
		// When
		comp.getDepartmentResponsibles().put(dept, emp);
		
		// Then
        assertEquals("Map contains department responsible", emp, comp.getDepartmentResponsibles().get(dept));
	}

}
