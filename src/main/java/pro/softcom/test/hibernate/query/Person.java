package pro.softcom.test.hibernate.query;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
//@formatter:off
@NamedQueries(value = { 
        @NamedQuery(
                name = Person.SELECT_PERSONS_WITHOUT_CATEGORIES, 
                query = "select p from Person p left join p.categories c group by p having count(c) = 0"),
                
        // We need to specify "where p1.salary is not null" to avoid that comparison with null salary returns false
        @NamedQuery(
                name = Person.SELECT_PERSON_WITH_MAX_SALARY, 
                query = "select p from Person p where p.salary >= ALL(select p1.salary from Person p1 where p1.salary is not null)"),
        
        @NamedQuery(
                name = Person.SELECT_COUNT_CATEGORIES, 
                query = "select p.id, COUNT(p.categories) from Person p group by p.id")
})
//@formatter:on
public class Person {

    public static final String SELECT_PERSONS_WITHOUT_CATEGORIES = "selectPersonsWithoutCategory";
    public static final String SELECT_PERSON_WITH_MAX_SALARY = "selectPersonsWithMaxSalary";
    public static final String SELECT_COUNT_CATEGORIES = "selectCountCategories";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private BigDecimal salary;

    @ManyToMany()
    @JoinTable(name = "person_category", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<Category>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

}
