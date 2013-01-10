package pro.softcom.test.hibernate.query;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
// TODO check, it is correct?
@Cleanup(phase = TestExecutionPhase.AFTER)
public class QueryTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Person.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    EntityManager em;

    /**
     * This shows group by / having aggregate functions. 
     * 
     * It perform a query to retrieve all persons having no category
     */
    @Test
    @UsingDataSet("query/initial.yml")
    public void testGroupByHaving() {

        // Given
        // Initial dataset

        // When
        // Query using JPQL
        TypedQuery<Person> q = em.createNamedQuery(Person.SELECT_PERSONS_WITHOUT_CATEGORIES, Person.class);

        // Then
        assertEquals(1, q.getResultList().size());

        // When
        // Query using Criteria
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> c = cb.createQuery(Person.class);
        Root<Person> person = c.from(Person.class);
        c.select(person);
        Join<Person, Category> categories = person.join("categories", JoinType.LEFT);
        c.groupBy(person);
        c.having(cb.equal(cb.count(categories), 0));
        q = em.createQuery(c);
        List<Person> result = q.getResultList();

        // Then
        assertEquals(1, result.size());
        assertEquals("Samuel", result.get(0).getFirstName());
    }

    /**
     * This shows usage of ALL expression in where clause. 
     * 
     * It perform a query to retrieve the person with max salary
     */
    @Test
    @UsingDataSet("query/initial.yml")
    public void testWhereAll() {

        // Given
        // Initial dataset

        // When
        // Query using JPQL
        TypedQuery<Person> q = em.createNamedQuery(Person.SELECT_PERSON_WITH_MAX_SALARY, Person.class);

        // Then
        assertEquals("Legros", q.getSingleResult().getLastName());

        // When
        // Query using Criteria
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> c = cb.createQuery(Person.class);
        Root<Person> person = c.from(Person.class);
        c.select(person);
        Subquery<BigDecimal> sc = c.subquery(BigDecimal.class);
        Root<Person> person1 = sc.from(Person.class);
        sc.select(person1.get("salary").as(BigDecimal.class));
        sc.where(cb.isNotNull(person1.get("salary")));
        c.where(cb.greaterThanOrEqualTo(person.get("salary").as(BigDecimal.class), cb.all(sc)));
        q = em.createQuery(c);

        // Then
        assertEquals("Legros", q.getSingleResult().getLastName());
    }
}
