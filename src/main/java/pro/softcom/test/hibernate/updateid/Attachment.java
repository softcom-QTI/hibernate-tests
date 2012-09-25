package pro.softcom.test.hibernate.updateid;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attachment implements Serializable {
	
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

    @Id
    protected Long id;
    
    @ManyToOne
    @JoinColumn(name="document_id")
	private Document document;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

    
}