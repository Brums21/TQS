package io.cucumber.skeleton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class BookSearchSteps {
    Library library = new Library();
	List<Book> result = new ArrayList<>();

    @Given("a book with the title {string}, written by {string}, published in {string}")
	public void addNewBook(final String title, final String author, final String publishedStr) {
		String[] published = publishedStr.split("-");
        Book book = new Book(title, author, iso8601Date(published[0], published[1], published[2]));
		library.addBook(book);
	}
 
	@When("the customer searches for books published between {string} and {string}")
	public void setSearchParameters(final String from, final String to) {
		result = library.findBooks(iso8601YearFrom(from), iso8601YearTo(to));
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size()).isEqualTo(booksFound);
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle()).isEqualTo(title);
	}

    @ParameterType("([0-9]{4})")
    public LocalDate iso8601YearFrom(String year){
        return LocalDate.of(Integer.parseInt(year), 1, 1);
    }

    @ParameterType("([0-9]{4})")
    public LocalDate iso8601YearTo(String year){
        return LocalDate.of(Integer.parseInt(year), 12, 31);
    }

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDate iso8601Date(String year, String month, String day){
		return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
	}


}
