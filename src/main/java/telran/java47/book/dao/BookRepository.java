package telran.java47.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

public interface BookRepository {

//	@Query("select b from Book b join Author a where a.AUTHORS_NAME=?1 and b_a.BOOK_ISBN = b.isbn ")
//	@Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = ?1")
	Stream<Book> findBooksByAuthorsName(String author);

//	@Query("select b from Book b join Publisher p where b.publisher=?1 ")
	Stream<Book> findBooksByPublisherPublisherName(Publisher publisher);

	boolean existsById(String isbn);

	Book save(Book book);

	Optional<Book> findById(String isbn);

	void deleteById(Book book);
	
	//@Query("SELECT b.publisher from Book b JOIN b.authors a WHERE a.name = ?1")
	//Stream<Publisher> findPublishersByAuthor(String author);

}
