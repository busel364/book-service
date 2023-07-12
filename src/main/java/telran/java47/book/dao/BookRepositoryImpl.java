package telran.java47.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Stream<Book> findBooksByAuthorsName(String author) {
		TypedQuery<Book> query = entityManager.createQuery("select b from Book b join b.authors a where a.name = ?1",Book.class);
		query.setParameter(1, author);
		return query.getResultStream();
	}

	@Override
	public Stream<Book> findBooksByPublisherPublisherName(Publisher publisher) {
		TypedQuery<Book> query = entityManager.createQuery("select b from Book b join b.publisher p where p.publisherName = ?1",Book.class);
		query.setParameter(1, publisher);
		return query.getResultStream();
	}

	@Override
	public boolean existsById(String isbn) {
		return entityManager.find(Book.class, isbn)!=null;
	}

	@Override
//	@Transactional
	public Book save(Book book) {
		entityManager.persist(book);
//		entityManager.merge(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(entityManager.find(Book.class, isbn));
	}

	@Override
	public void deleteById(Book book) {
		entityManager.remove(book);
	}

}
