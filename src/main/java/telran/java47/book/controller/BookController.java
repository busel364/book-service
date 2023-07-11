package telran.java47.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

	final BookService bookService;

	@PostMapping("/book")
	boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	BookDto findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}

	@DeleteMapping("/book/{isbn}")
	BookDto removeBook(@PathVariable String isbn) {
		return bookService.removeBook(isbn);
	}

	@PutMapping("/book/{isbn}/title/{title}")
	BookDto updateBookTitle(@PathVariable String isbn, @PathVariable String title) {
		return bookService.updateBookTitle(isbn, title);
	}

	@GetMapping("/books/author/{author}")
	Iterable<BookDto> findBookByAuthor(@PathVariable String author){
		return bookService.findBookByAuthor(author);
	}

	@GetMapping("/books/publisher/{publisher}")
	Iterable<BookDto> findBookByPublisher(@PathVariable String publisher){
		return bookService.findBookByPublisher(publisher);
	}

	@GetMapping("/authors/book/{isbn}")
	Iterable<AuthorDto> findBookAuthors(@PathVariable String isbn){
		return bookService.findBookAuthors(isbn);
	}

	@GetMapping("/publishers/author/{author}")
	Iterable<String> findPublishersByAuthor(String author){
		return bookService.findPublishersByAuthor(author);
	}

	@DeleteMapping("/author/{author}")
	AuthorDto removeAuthor(String author){
		return bookService.removeAuthor(author);
	}
	}
