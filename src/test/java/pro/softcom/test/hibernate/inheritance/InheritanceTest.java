package pro.softcom.test.hibernate.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
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
@Transactional(TransactionMode.COMMIT)
//TODO check, it is correct?
@Cleanup(phase = TestExecutionPhase.AFTER)
public class InheritanceTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Employee.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    /**
     * Demonstrates simple polymorphic queries
     */
    @Test
    @UsingDataSet("inheritance/initial.yml")
    public void testPolymorphicQuery() {

        // Given
        // Initial dataset

        // When
        Query q1 = em.createQuery("select e from Employee e");
        Query q2 = em.createQuery("select pe from PartTimeEmployee pe");

        // Then
        assertEquals(2, q1.getResultList().size());
        assertEquals(1, q2.getResultList().size());
    }

    /**
     * EntityManager.find() is polymorphic as well 
     */
    @Test
    @UsingDataSet("inheritance/initial.yml")
    public void testFind() {

        // Given
        // Initial dataset

        // When
        Employee e1 = em.find(Employee.class, 2L);
        Employee e2 = em.find(PartTimeEmployee.class, 1L);

        // Then
        assertNotNull(e1);
        assertNull(e2);
    }
}
