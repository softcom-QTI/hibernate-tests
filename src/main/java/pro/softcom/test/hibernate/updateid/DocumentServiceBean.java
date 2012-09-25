package pro.softcom.test.hibernate.updateid;

import java.util.HashSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A test showing that updating id of an entity does not work. 
 * Solution here is to remove and persist new entities, or better, use surrogate pk that will
 * never need to be updated.
 * 
 * @author samuel
 *
 */
@Stateless
public class DocumentServiceBean {

	@PersistenceContext EntityManager em;
	
// altering id does not work
	
//	public void updateDocumentId(Document d) {
//		
//		d = em.find(Document.class, d.getId());
//		d.setId(2L);
//		for (Attachment a : d.getAttachments()) {
//			a.setId(a.getId() + 10);
//		}
//	}
	
	public void updateDocumentId(Document d) {
		
		d = em.find(Document.class, d.getId());
		for (Attachment a : d.getAttachments()) {
			em.remove(a);
		}
		em.remove(d);
		em.flush();
		
		d.setId(2L);
		for (Attachment a : d.getAttachments()) {
			a.setId(a.getId() + 10);
		}
		
		// Replacing the collection is a trick to avoid "HibernateException: Found two representations of same collection"
		// See https://forum.hibernate.org/viewtopic.php?p=2231400
		d.setAttachments(new HashSet<Attachment>(d.getAttachments()));
		em.persist(d);
		for (Attachment a : d.getAttachments()) {
			em.persist(a);
		}
		
		
	}
	
	public Document createDocument() {
	
		Document d = new Document();
		d.setId(1L);
		d.setTitle("lala");
		em.persist(d);
		
		Attachment a1 = new Attachment();
		a1.setId(11L);
		a1.setDocument(d);
		d.getAttachments().add(a1);
		em.persist(a1);
		
		Attachment a2 = new Attachment();
		a2.setId(12L);
		a2.setDocument(d);
		d.getAttachments().add(a2);
		em.persist(a2);
		
		return d;
	}
	
}
