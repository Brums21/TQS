package io.cucumber.skeleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();
 
	public void addBook(final Book book) {
		store.add(book);
	}
 
	public List<Book> findBooks(final LocalDate from, final LocalDate to) {
 
		return store.stream().filter(book -> {
			return book.getPublished().compareTo(from)>=0 && book.getPublished().compareTo(to)<=0;
		}).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
	}
}
