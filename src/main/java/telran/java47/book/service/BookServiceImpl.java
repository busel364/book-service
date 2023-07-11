package telran.java47.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookRepository;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));

		// Authors
		Set<Author> authors = bookDto.getAuthors().stream().map(a -> authorRepository.findById(a.getName())
				.orElse(authorRepository.save(modelMapper.map(a, Author.class)))).collect(Collectors.toSet());

		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);

		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
//	@Transactional(readOnly = true)
	public Iterable<BookDto> findBookByAuthor(String author) {
		Author authorObj = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		return authorObj.getBooks().stream().map(b->modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
//		return bookRepository.findBooksByAuthorsName(author).map(b -> modelMapper.map(b, BookDto.class))
//				.collect(Collectors.toList());
	}

	@Override
//	@Transactional(readOnly = true)
	public Iterable<BookDto> findBookByPublisher(String publisher) {
		Publisher publisherObj = publisherRepository.findById(publisher).orElseThrow(EntityNotFoundException::new);
		return publisherObj.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<String> findPublishersByAuthor(String author) {
		return publisherRepository.findPublishersByAuthor(author);
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String author) {
		Author authorObj = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
//		bookRepository.findBooksByAuthorsName(author).forEach(b->bookRepository.delete(b));
		authorRepository.delete(authorObj);
		return modelMapper.map(authorObj, AuthorDto.class);
	}

}
