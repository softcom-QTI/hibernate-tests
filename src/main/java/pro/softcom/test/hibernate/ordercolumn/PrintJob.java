package pro.softcom.test.hibernate.ordercolumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PrintJob {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@ManyToOne
	private PrintQueue queue;
	
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PrintQueue getQueue() {
		return queue;
	}

	public void setQueue(PrintQueue queue) {
		this.queue = queue;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
