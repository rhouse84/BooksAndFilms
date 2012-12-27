package booksandfilms.client;

import java.util.ArrayList;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.shared.exception.CannotDeleteException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

//The RemoteServiceRelativePath annotation must match the servlet URL configured in
//web.xml
@RemoteServiceRelativePath("persistentservice")
public interface PersistentService extends RemoteService {
  
  Author updateAuthor(Author author) throws IllegalArgumentException;
  void deleteAuthor(Author author) throws IllegalArgumentException, CannotDeleteException;
  Director updateDirector(Director director) throws IllegalArgumentException;
  void deleteDirector(Director director) throws IllegalArgumentException, CannotDeleteException;
  void persistAuthorList(ArrayList<ArrayList<String>> authors) throws IllegalArgumentException;
  void persistDirectorList(ArrayList<ArrayList<String>> directors) throws IllegalArgumentException;
  Book updateBook(Book book) throws IllegalArgumentException;
  void deleteBook(Book book) throws IllegalArgumentException, CannotDeleteException;
  Film updateFilm(Film film) throws IllegalArgumentException;
  void deleteFilm(Film film) throws IllegalArgumentException, CannotDeleteException;
  void persistBookList(ArrayList<ArrayList<String>> books) throws IllegalArgumentException;
  void persistFilmList(ArrayList<ArrayList<String>> films) throws IllegalArgumentException;
  void persistQuoteList(ArrayList<ArrayList<String>> quotes) throws IllegalArgumentException;
  void updateTopic(Topic topic) throws IllegalArgumentException;
  void deleteTopic(Topic topic) throws IllegalArgumentException, CannotDeleteException;
  Quote updateQuote(Quote quote) throws IllegalArgumentException;
  void deleteQuote(Quote quote) throws IllegalArgumentException;
  void updateUser(UserAccount user) throws IllegalArgumentException;
  void deleteUser(UserAccount user) throws IllegalArgumentException, CannotDeleteException;
}