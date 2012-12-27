package booksandfilms.client.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity
public class Book extends Media implements Serializable {

	private static final long serialVersionUID = 3L;
	@Id
	Long id;
	@Indexed private String title;
	@Indexed private Long authorId;
	@Indexed private Long userId;
	@Indexed private Long topicId;
	@Unindexed private String genre;
	@Indexed private byte rating;
	@Unindexed private String notes;
	@Unindexed private Date createDate;
	@Indexed private Date readDate;
	@Unindexed private String authorName;
	@Unindexed private String topicDesc;
	
	public Book() {}
	public Book(Long id)
	{
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public byte getRating() {
		return rating;
	}
	public void setRating(byte rating) {
		this.rating = rating;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getTopicDesc() {
		return topicDesc;
	}
	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}
	public String getRatingDesc() {
		if (this.rating == 1) {
			return "Bad";
		} else if (this.rating == 2) {
			return "OK";
		} else if (this.rating == 3) {
			return "Good";
		} else {
			return "Great";
		}
	}
	
	public enum SortParameter {
		TITLE_ASCENDING,
		TITLE_DESCENDING,
		AUTHOR_ASCENDING,
		AUTHOR_DESCENDING,
		RATING_ASCENDING,
		RATING_DESCENDING,
		GENRE_ASCENDING,
		GENRE_DESCENDING,
		READ_ASCENDING,
		READ_DESCENDING
	}

	public static Comparator<Book> getComparator(SortParameter... sortParameters) {
		return new BookComparator(sortParameters);
	}

    private static class BookComparator implements Comparator<Book> {
        private SortParameter[] parameters;

        private BookComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(Book o1, Book o2) {
        	int comparison;
        	for (SortParameter parameter : parameters) {
        		switch (parameter) {
        		case TITLE_ASCENDING:
        			comparison = o1.title.compareTo(o2.title);
        			if (comparison != 0) return comparison;
        			break;
        		case TITLE_DESCENDING:
        			comparison = o2.title.compareTo(o1.title);
        			if (comparison != 0) return comparison;
        			break;
        		case AUTHOR_ASCENDING:
        			comparison = o1.getAuthorName().toLowerCase().compareTo(o2.getAuthorName().toLowerCase());
        			if (comparison != 0) return comparison;
        			break;
        		case AUTHOR_DESCENDING:
        			comparison = o2.getAuthorName().toLowerCase().compareTo(o1.getAuthorName().toLowerCase());
        			if (comparison != 0) return comparison;
        			break;
        		case RATING_ASCENDING:
        			comparison = o1.rating - o2.rating;
        			if (comparison != 0) return comparison;
        			break;
        		case RATING_DESCENDING:
        			comparison = o2.rating - o1.rating;
        			if (comparison != 0) return comparison;
        			break;
        		case GENRE_ASCENDING:
        			comparison = o1.getGenre().compareTo(o2.getGenre());
        			if (comparison != 0) return comparison;
        			break;
        		case GENRE_DESCENDING:
        			comparison = o2.getGenre().compareTo(o1.getGenre());
        			if (comparison != 0) return comparison;
        			break;
        		case READ_ASCENDING:
        			comparison = o1.readDate.compareTo(o2.readDate);
        			if (comparison != 0) return comparison;
        			break;
        		case READ_DESCENDING:
        			comparison = o2.readDate.compareTo(o1.readDate);
        			if (comparison != 0) return comparison;
        			break;
        		}
        	}
        	return 0;
        }
    }
}
