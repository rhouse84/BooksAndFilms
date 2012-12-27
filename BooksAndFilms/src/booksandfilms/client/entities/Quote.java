package booksandfilms.client.entities;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity
public class Quote extends Media implements Serializable {

	private static final long serialVersionUID = 2L;
	@Id
	Long id;
	@Indexed private Long bookId;
	@Indexed private Long filmId;
	@Indexed private Long userId;
	@Unindexed private String characterName;
	@Unindexed private String quoteText;
	@Unindexed private String bookTitle;
	@Unindexed private String filmTitle;

	public Quote() {}
	public Quote(Long id)
	{
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getFilmId() {
		return filmId;
	}
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getQuoteText() {
		return quoteText;
	}
	public void setQuoteText(String quoteText) {
		this.quoteText = quoteText;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public enum SortParameter {
		ID_ASCENDING,
		BOOK_TITLE_ASCENDING,
		BOOK_TITLE_DESCENDING,
		FILM_TITLE_ASCENDING,
		FILM_TITLE_DESCENDING
	}

	public static Comparator<Quote> getComparator(SortParameter... sortParameters) {
		return new QuoteComparator(sortParameters);
	}

    private static class QuoteComparator implements Comparator<Quote> {
        private SortParameter[] parameters;

        private QuoteComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(Quote o1, Quote o2) {
        	int comparison;
        	for (SortParameter parameter : parameters) {
        		switch (parameter) {
        		case ID_ASCENDING:
        			comparison = (int) (o1.getId() - o2.getId());
        			if (comparison != 0) return comparison;
        			break;
        		case BOOK_TITLE_ASCENDING:
        			comparison = o1.getBookTitle().compareTo(o2.getBookTitle());
        			if (comparison != 0) return comparison;
        			break;
        		case BOOK_TITLE_DESCENDING:
        			comparison = o2.getBookTitle().compareTo(o1.getBookTitle());
        			if (comparison != 0) return comparison;
        			break;
        		case FILM_TITLE_ASCENDING:
        			comparison = o1.getFilmTitle().compareTo(o2.getFilmTitle());
        			if (comparison != 0) return comparison;
        			break;
        		case FILM_TITLE_DESCENDING:
        			comparison = o2.getFilmTitle().compareTo(o1.getFilmTitle());
        			if (comparison != 0) return comparison;
        			break;
        		}
        	}
        	return 0;
        }
    }
}
