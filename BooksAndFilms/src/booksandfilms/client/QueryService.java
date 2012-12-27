package booksandfilms.client;

import java.util.ArrayList;
import java.util.Map;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("queryservice")
public interface QueryService extends RemoteService {
	Map<Long, Author> getAllAuthors();
	Map<Long, Director> getAllDirectors();
	Author getAuthorById(Long id);
	Director getDirectorById(Long id);
	Book getBookById(Long id);
	Film getFilmById(Long id);
	ArrayList<UserAccount> getAllUsers();
	UserAccount getUserById(Long id);
	ArrayList<Topic> getAllTopics();
	Topic getTopicById(Long id);
	Quote getQuoteById(Long id);
	ArrayList<Map<Long, Media>> getBookData();
	ArrayList<Map<Long, Media>> getFilmData();
	Map<Long, Media> getQuoteData();
	ArrayList<Media> getMediaData(String searchString, String mediaName);
}
