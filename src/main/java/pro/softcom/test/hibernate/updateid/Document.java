package pro.softcom.test.hibernate.updateid;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Document implements Serializable {
	
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

    @Id
    protected Long id;
    
	private String title;

	@OneToMany(mappedBy="document")
	private Set<Attachment> attachments = new HashSet<Attachment>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	
}