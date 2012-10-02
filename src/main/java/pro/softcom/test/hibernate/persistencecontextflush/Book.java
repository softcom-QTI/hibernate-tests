package pro.softcom.test.hibernate.persistencecontextflush;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name=Book.SELECT_ALL_BOOKS, query= "select b from Book b")
public class Book {

    public static final String SELECT_ALL_BOOKS = "selectAllBooks";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String isbn;

	public Long getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String name) {
		this.isbn = name;
	}

}
