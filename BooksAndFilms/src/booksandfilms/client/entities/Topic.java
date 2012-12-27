package booksandfilms.client.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;

@Cached
@Entity
public class Topic extends Media implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	Long id;
	@Indexed private String description;
	@Indexed private Long userId;
	
	public Topic() {}
	public Topic(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public static Comparator<Topic> getComparator() {
		return new TopicComparator();
	}

    private static class TopicComparator implements Comparator<Topic> {

        private TopicComparator() {}

        public int compare(Topic o1, Topic o2) {
        	int comparison;
        	comparison = o1.description.toLowerCase().compareTo(o2.description.toLowerCase());
     		if (comparison != 0) return comparison;
        	return 0;
        }
    }

	public static ArrayList<Topic> sortTopics(Map<Long, Media> unSorted) {
		ArrayList<Topic> sorted = new ArrayList<Topic>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Topic topic = (Topic) thisEntry.getValue();
			sorted.add(topic);
		}
		Comparator<Topic> cp = Topic.getComparator();
		Collections.sort(sorted, cp);
		
		return sorted;
	}
}
