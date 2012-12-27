package booksandfilms.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import booksandfilms.client.QueryService;
import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.shared.SharedConstants;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class QueryServiceImpl extends RemoteServiceServlet implements
		QueryService {

	@Override
	public Map<Long, Author> getAllAuthors() {
		DAO dao = new DAO();
        
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        Iterable<Key<Author>> allKeys = dao.ofy().query(Author.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Author>, Author> authorsTemp = dao.ofy().get(allKeys);
        HashMap<Long, Author> authors = new HashMap<Long, Author>();
		@SuppressWarnings("rawtypes")
		Iterator iterator = authorsTemp.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Author author = (Author) thisEntry.getValue();
			authors.put(author.getId(), author);
        }
        
        return authors;
	}

	@Override
	public Map<Long, Director> getAllDirectors() {
		DAO dao = new DAO();
        
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        Iterable<Key<Director>> allKeys = dao.ofy().query(Director.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Director>, Director> directorsTemp = dao.ofy().get(allKeys);
        HashMap<Long, Director> directors = new HashMap<Long, Director>();
        
		@SuppressWarnings("rawtypes")
		Iterator iterator = directorsTemp.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Director director = (Director) thisEntry.getValue();
			directors.put(director.getId(), director);
        }
        
        return directors;
	}

	@Override
	public ArrayList<Map<Long, Media>> getBookData() {
		DAO dao = new DAO();
        
       	UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        ArrayList<Map<Long, Media>> data = new ArrayList<Map<Long, Media>>();

        Iterable<Key<Book>> allKeys = dao.ofy().query(Book.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Book>, Book> booksTemp = dao.ofy().get(allKeys);
        Map<Long, Media> books = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator = booksTemp.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Book book = (Book) thisEntry.getValue();
			books.put(book.getId(), book);
        }

        Iterable<Key<Author>> allAuthorKeys = dao.ofy().query(Author.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Author>, Author> authorsTemp = dao.ofy().get(allAuthorKeys);
        HashMap<Long, Media> authors = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator2 = authorsTemp.entrySet().iterator();
        while(iterator2.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator2.next();
			Author author = (Author) thisEntry.getValue();
			authors.put(author.getId(), author);
        }
        
        Iterable<Key<Topic>> allTopicKeys = dao.ofy().query(Topic.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Topic>, Topic> topicsTemp = dao.ofy().get(allTopicKeys);
        HashMap<Long, Media> topics = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator6 = topicsTemp.entrySet().iterator();
        while(iterator6.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator6.next();
			Topic topic = (Topic) thisEntry.getValue();
			topics.put(topic.getId(), topic);
        }
        
        data.add(books);
        data.add(authors);
        data.add(topics);
        
        return data;
	}

	@Override
	public ArrayList<Map<Long, Media>> getFilmData() {
		DAO dao = new DAO();
        
       	UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        ArrayList<Map<Long, Media>> data = new ArrayList<Map<Long, Media>>();

        Iterable<Key<Film>> allFilmKeys = dao.ofy().query(Film.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Film>, Film> filmsTemp = dao.ofy().get(allFilmKeys);
        HashMap<Long, Media> films = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator3 = filmsTemp.entrySet().iterator();
        while(iterator3.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator3.next();
			Film film = (Film) thisEntry.getValue();
			films.put(film.getId(), film);
        }
        
        Iterable<Key<Director>> allDirectorKeys = dao.ofy().query(Director.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Director>, Director> directorsTemp = dao.ofy().get(allDirectorKeys);
        HashMap<Long, Media> directors = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator4 = directorsTemp.entrySet().iterator();
        while(iterator4.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator4.next();
			Director director = (Director) thisEntry.getValue();
			directors.put(director.getId(), director);
        }

        data.add(films);
        data.add(directors);

        return data;
	}
	
    @Override
	public Author getAuthorById(Long id) {
		DAO dao = new DAO();
		Author author = dao.ofy().get(Author.class, id);
		
		return author;
	}

	@Override
	public Director getDirectorById(Long id) {
		DAO dao = new DAO();
		Director director = dao.ofy().get(Director.class, id);
		
		return director;
	}

	@Override
	public Book getBookById(Long id) {
		DAO dao = new DAO();
		Book book = dao.ofy().get(Book.class, id);
   		Author author = dao.ofy().get(Author.class, book.getAuthorId());
   		book.setAuthorName(author.getName());
   		if (book.getTopicId() != null) {
   			Topic topic = dao.ofy().get(Topic.class, book.getTopicId());
   			book.setTopicDesc(topic.getDescription());
   		}
		
		return book;
	}

	@Override
	public Film getFilmById(Long id) {
		DAO dao = new DAO();
		Film film = dao.ofy().get(Film.class, id);
   		Director director = dao.ofy().get(Director.class, film.getDirectorId());
   		film.setDirectorName(director.getName());
   		if (film.getTopicId() != null) {
   			Topic topic = dao.ofy().get(Topic.class, film.getTopicId());
   			film.setTopicDesc(topic.getDescription());
   		}
		
		return film;
	}

	@Override
	public ArrayList<UserAccount> getAllUsers() {
		DAO dao = new DAO();
        
        Query<UserAccount> q = dao.ofy().query(UserAccount.class).order("name");
        ArrayList<UserAccount> users = new ArrayList<UserAccount>();
        
        //Loop the query results and add to the array
        for (UserAccount fetched : q) {
          users.add(fetched);
        }
        
        return users;
	}

	@Override
	public UserAccount getUserById(Long id) {
		DAO dao = new DAO();
		UserAccount user = dao.ofy().get(UserAccount.class, id);
		
		return user;
	}

	@Override
	public ArrayList<Topic> getAllTopics() {
		DAO dao = new DAO();
        
       	UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        Query<Topic> q = dao.ofy().query(Topic.class).filter("userId", currentUser.getId()).order("description");
        ArrayList<Topic> topics = new ArrayList<Topic>();
        
        //Loop the query results and add to the array
        for (Topic fetched : q) {
          topics.add(fetched);
        }
        
        return topics;
	}

	@Override
	public Topic getTopicById(Long id) {
		DAO dao = new DAO();
		Topic topic = dao.ofy().get(Topic.class, id);
		
		return topic;
	}

	@Override
	public Map<Long, Media> getQuoteData() {
		DAO dao = new DAO();

		UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        Iterable<Key<Quote>> allKeys = dao.ofy().query(Quote.class).filter("userId", currentUser.getId()).fetchKeys();
        Map<Key<Quote>, Quote> quotesTemp = dao.ofy().get(allKeys);
        Map<Long, Media> quotes = new HashMap<Long, Media>();
		@SuppressWarnings("rawtypes")
		Iterator iterator = quotesTemp.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Quote quote = (Quote) thisEntry.getValue();
			quotes.put(quote.getId(), quote);
        }
        
        return quotes;

	}
	
	@Override
	public Quote getQuoteById(Long id) {
		DAO dao = new DAO();
		Quote quote = dao.ofy().get(Quote.class, id);
		
		return quote;
	}

	@Override
	public ArrayList<Media> getMediaData(String searchString, String mediaName) {
		DAO dao = new DAO();

		UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
		
		ArrayList<Media> searchData = new ArrayList<Media>();
		if (mediaName.equals(SharedConstants.AUTHOR)) {
			Query<Author> media = dao.ofy().query(Author.class).filter("userId", currentUser.getId()).filter("namelc >=", searchString.toLowerCase()).filter("namelc <", searchString.toLowerCase() + "\uFFFD");
			QueryResultIterator<Author> iterator = media.iterator();
			while(iterator.hasNext()) {
				Media mediaItem = (Author) iterator.next();
				searchData.add(mediaItem);
			}
		} else {
        	Query<Director> media = dao.ofy().query(Director.class).filter("userId", currentUser.getId()).filter("namelc >=", searchString.toLowerCase()).filter("namelc <", searchString.toLowerCase() + "\uFFFD");
			QueryResultIterator<Director> iterator = media.iterator();
			while(iterator.hasNext()) {
				Media mediaItem = (Director) iterator.next();
				searchData.add(mediaItem);
			}
		}
		
        return searchData;

	}
	
}
