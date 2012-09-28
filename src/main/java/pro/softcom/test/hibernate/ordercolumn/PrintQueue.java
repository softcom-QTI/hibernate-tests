package pro.softcom.test.hibernate.ordercolumn;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="print_queue")
public class PrintQueue {

    @Id
    @GeneratedValue(generator="print_queue_gen")
    @SequenceGenerator(name="print_queue_gen", sequenceName="print_queue_seq")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "print_queue_print_job", joinColumns = @JoinColumn(name = "queue_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
    @OrderColumn(name = "print_order")
    private List<PrintJob> jobs = new ArrayList<PrintJob>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrintJob> getJobs() {
        return jobs;
    }

}
