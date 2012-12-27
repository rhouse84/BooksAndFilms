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
public class Author extends Media implements Serializable {

	private static final long serialVersionUID = 2L;
	@Id
	Long id;
	@Unindexed private String name;
	@Indexed private String namelc;
	@Indexed private Long userId;
	
	public Author() {}
	public Author(Long id)
	{
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameLc() {
		return namelc;
	}
	public void setNameLc(String namelc) {
		this.namelc = namelc;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public enum SortParameter {
		NAME_ASCENDING,
		NAME_DESCENDING
	}

	public static Comparator<Author> getComparator(SortParameter... sortParameters) {
		return new AuthorComparator(sortParameters);
	}

    private static class AuthorComparator implements Comparator<Author> {
        private SortParameter[] parameters;

        private AuthorComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(Author o1, Author o2) {
        	int comparison;
        	for (SortParameter parameter : parameters) {
        		switch (parameter) {
        		case NAME_ASCENDING:
        			comparison = o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
        			if (comparison != 0) return comparison;
        			break;
        		case NAME_DESCENDING:
        			comparison = o2.name.toLowerCase().compareTo(o1.name.toLowerCase());
        			if (comparison != 0) return comparison;
        			break;
        		}
        	}
        	return 0;
        }
    }
}
