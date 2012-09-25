package pro.softcom.test.hibernate.mapkeybyentity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MapKeyByEntityTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(Company.class, Department.class, Employee.class)
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	/**
     * Company has a map of entities (Employee) keyed by entity (Department)
	 */
	@Test
	public void testPersist() throws Exception {

		Company company = new Company();
		em.persist(company);
		
		Employee roger = new Employee();
		roger.setFirstName("Roger");		
		roger.setLastName("Legros");
		roger.setCompany(company);
		em.persist(roger);
		
		Department accounting = new Department();
		accounting.setName("accounting");
		em.persist(accounting);
		
		company.getDepartmentResponsibles().put(accounting, roger);
	}

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from PrintJob").executeUpdate();
		em.createQuery("delete from PrintQueue").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

}
