package booksandfilms.client;

import java.util.ArrayList;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PersistentServiceAsync {

    void updateAuthor(Author author, AsyncCallback<Author> callback);
    void deleteAuthor(Author author, AsyncCallback<Void> callback);
    void updateDirector(Director director, AsyncCallback<Director> callback);
    void deleteDirector(Director director, AsyncCallback<Void> callback);
    void persistAuthorList(ArrayList<ArrayList<String>> authors, AsyncCallback<Void> callback);
    void persistDirectorList(ArrayList<ArrayList<String>> directors, AsyncCallback<Void> callback);
    void updateBook(Book book, AsyncCallback<Book> callback);
    void deleteBook(Book book, AsyncCallback<Void> callback);
    void updateFilm(Film film, AsyncCallback<Film> callback);
    void deleteFilm(Film film, AsyncCallback<Void> callback);
    void persistBookList(ArrayList<ArrayList<String>> books, AsyncCallback<Void> callback);
    void persistFilmList(ArrayList<ArrayList<String>> films, AsyncCallback<Void> callback);
    void persistQuoteList(ArrayList<ArrayList<String>> quotes, AsyncCallback<Void> callback);
    void updateTopic(Topic topic, AsyncCallback<Void> callback);
    void deleteTopic(Topic topic, AsyncCallback<Void> callback);
    void updateQuote(Quote quote, AsyncCallback<Quote> callback);
    void deleteQuote(Quote quote, AsyncCallback<Void> callback);
    void updateUser(UserAccount user, AsyncCallback<Void> callback);
    void deleteUser(UserAccount user, AsyncCallback<Void> callback);
}