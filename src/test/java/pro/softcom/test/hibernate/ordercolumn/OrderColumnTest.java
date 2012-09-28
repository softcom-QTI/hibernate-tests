package pro.softcom.test.hibernate.ordercolumn;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Comparator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
@Cleanup(phase = TestExecutionPhase.NONE)
public class OrderColumnTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(PrintQueue.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    /**
     * This shows persistent list ordering using @OrderColumn.
     * 
     * @OrderColumn is not supported by Hibernate when applied on the inverse
     * side of a @OneToMany relationship (it is simply ignored).
     * See https://hibernate.onjira.com/browse/HHH-5732
     * 
     * This is why we define an uni-directional OneToMany relationship
     */
    @Test
    @ShouldMatchDataSet("ordercolumn/expected.yml")
    @InSequence(0)
    public void testPersistPrintJobs() {

        // Given
        PrintQueue queue = new PrintQueue();
        queue.setName("test");
        em.persist(queue);

        PrintJob job = new PrintJob();
        job.setQueue(queue);
        job.setContent("ccc");
        queue.getJobs().add(job);
        em.persist(job);

        job = new PrintJob();
        job.setQueue(queue);
        job.setContent("a");
        queue.getJobs().add(job);
        em.persist(job);

        job = new PrintJob();
        job.setQueue(queue);
        job.setContent("bb");
        queue.getJobs().add(job);
        em.persist(job);

        // When

        // Sort by content alpha
        Collections.sort(queue.getJobs(), new Comparator<PrintJob>() {

            @Override
            public int compare(PrintJob p1, PrintJob p2) {
                return p1.getContent().compareTo(p2.getContent());
            }
        });

        // Then
        // Expected dataset
    }

    @Test
    @InSequence(1)
    public void testFind() {

        // Given
        // Previous test

        // When
        PrintQueue queue = em.find(PrintQueue.class, 1L);

        // Then
        assertEquals("First element in list is a", "a", queue.getJobs().get(0).getContent());
        assertEquals("Last element in list is ccc", "ccc", queue.getJobs().get(2).getContent());
    }
}