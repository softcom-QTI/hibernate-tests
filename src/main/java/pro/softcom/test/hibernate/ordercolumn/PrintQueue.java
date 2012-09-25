package pro.softcom.test.hibernate.ordercolumn;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class PrintQueue {
	
    @Id 
    private String name;
    
    @OneToMany(mappedBy="queue")
    @OrderColumn(name="PRINT_ORDER")
    private List<PrintJob> jobs = new ArrayList<PrintJob>();

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
