package telran.java47.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

public interface BookRepository extends PagingAndSortingRepository<Book, String> {

//	@Query("select b from Book b join Author a where a.AUTHORS_NAME=?1 and b_a.BOOK_ISBN = b.isbn ")
	@Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = ?1")
	Stream<Book> findBooksByAuthor(String author);

//	@Query("select b from Book b join Publisher p where b.publisher=?1 ")
	Stream<Book> findBooksByPublisher(Publisher publisher);
	
	@Query("SELECT b.publisher from Book b JOIN b.authors a WHERE a.name = ?1")
	Stream<Publisher> findPublishersByAuthor(String author);

}
