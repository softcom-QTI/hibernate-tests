package pro.softcom.test.hibernate.updateid;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pro.softcom.test.hibernate.Resources;
import pro.softcom.test.hibernate.updateid.Attachment;
import pro.softcom.test.hibernate.updateid.Document;
import pro.softcom.test.hibernate.updateid.DocumentServiceBean;

@RunWith(Arquillian.class)
public class DocumentUpdateTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(Document.class, Attachment.class, DocumentServiceBean.class, Resources.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	DocumentServiceBean documentService;

	@Test
	public void testUpdateDocumentId() throws Exception {
		Document d = documentService.createDocument();
		
		documentService.updateDocumentId(d);
	}

}
