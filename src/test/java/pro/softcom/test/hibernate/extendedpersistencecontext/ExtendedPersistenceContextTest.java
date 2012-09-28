package pro.softcom.test.hibernate.extendedpersistencecontext;

import static org.junit.Assert.*;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
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

	@EJB
	DepartmentManager departmentManager;
	
	/**
     * Test is not transactional, DepartmentManager starts 4 transactions
	 */
	@Test
    @UsingDataSet("extendedpersistencecontext/initial.yml")
    @ShouldMatchDataSet("extendedpersistencecontext/expected.yml")
	public void testSetNameAddEmployee() throws Exception {

	    // Given 
	    // Initial dataset
	    
	    // When
	    departmentManager.init(1L);
        departmentManager.setName("Technology", false);
	    departmentManager.addEmployee(1L);

        departmentManager.setName("Accounting", true);

        // A method rollbacking the transaction seems to clear the extended persistence context
        // Following call has no effect because dept in detached
        departmentManager.setName("Bla", false);

        // Then
	    // Expected dataset
        assertEquals("Department has 1 employee", 1, departmentManager.getEmployeeCount());
        departmentManager.finished();
	}

}
