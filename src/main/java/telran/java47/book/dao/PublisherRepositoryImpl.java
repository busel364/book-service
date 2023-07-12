package telran.java47.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java47.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<String> findPublishersByAuthor(String author) {
		TypedQuery<String> query = entityManager.createQuery("SELECT distinct p.publisherName from Book b JOIN b.authors a join b.publisher p WHERE a.name = ?1",String.class);
		query.setParameter(1,  author);
		return query.getResultList();
	}

	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		TypedQuery<Publisher> query = entityManager.createQuery("SELECT distinct p from Book b JOIN b.authors a join b.publisher p WHERE a.name = ?1",Publisher.class);
		query.setParameter(1,  authorName);
		return query.getResultStream();
	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(entityManager.find(Publisher.class, publisher));
	}

	@Override
//	@Transactional
	public Publisher save(Publisher publisher) {
		entityManager.persist(publisher);
//		entityManager.merge(publisher);
		return publisher;
	}

}
