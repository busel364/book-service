package telran.java47.book.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java47.book.model.Author;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Optional<Author> findById(String authorName) {
		return Optional.ofNullable(entityManager.find(Author.class, authorName));
	}

	@Override
//	@Transactional
	public Author save(Author author) {
//		entityManager.merge(author);
		entityManager.persist(author);
		return author;
	}

	@Override
	@Transactional
	public void deleteById(Author author) {

		entityManager.remove(author);
	}

}
