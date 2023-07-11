package telran.java47.book.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Publisher;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher, String> {

	 @Query("SELECT distinct p.publisherName from Book b JOIN b.authors a join b.publisher p WHERE a.name = ?1")
	 List<String> findPublishersByAuthor(String author);
	 
	 Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);
}
