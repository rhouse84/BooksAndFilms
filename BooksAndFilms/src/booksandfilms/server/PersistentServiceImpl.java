package booksandfilms.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import booksandfilms.client.PersistentService;
import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.shared.exception.CannotDeleteException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Query;


@SuppressWarnings("serial")
public class PersistentServiceImpl extends RemoteServiceServlet implements
PersistentService {
    
    //Method must be present in Service Interface and Async Interface
    public Author updateAuthor(Author author) {
    	
		DAO dao = new DAO();

		UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        if (author.getUserId() == null) {
            author.setUserId(currentUser.getId());
        }
        author.setNameLc(author.getName().toLowerCase());

        dao.ofy().put(author);
        assert author.getId() != null;
        Author fetched = dao.ofy().get(Author.class, author.getId());

        Query<Book> q = dao.ofy().query(Book.class).filter("userId", currentUser.getId()).filter("authorId", author.getId());
        for (Book book : q) {
            book.setAuthorName(author.getName());
            dao.ofy().put(book);
        }
        
        return fetched;
        
    }

    public void deleteAuthor(Author author) throws CannotDeleteException {
    	
		DAO dao = new DAO();
        Query<Book> b = dao.ofy().query(Book.class).filter("authorId", author.getId());
        if (b.count() > 0) {
        	throw new CannotDeleteException();
        }
		
        dao.ofy().delete(author);

    }

    public Director updateDirector(Director director) {
    	
		DAO dao = new DAO();
        
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        if (director.getUserId() == null) {
            director.setUserId(currentUser.getId());
        }
    	director.setNameLc(director.getName().toLowerCase());

        dao.ofy().put(director);
        assert director.getId() != null;
        Director fetched = dao.ofy().get(Director.class, director.getId());

        Query<Film> q = dao.ofy().query(Film.class).filter("userId", currentUser.getId()).filter("directorId", director.getId());
        for (Film film : q) {
            film.setDirectorName(director.getName());
            dao.ofy().put(film);
        }
        
        return fetched;
        
    }

    public void deleteDirector(Director director) throws CannotDeleteException {
    	
		DAO dao = new DAO();
        Query<Film> b = dao.ofy().query(Film.class).filter("directorId", director.getId());
        if (b.count() > 0) {
        	throw new CannotDeleteException();
        }
		
        dao.ofy().delete(director);

    }

    public void persistAuthorList(ArrayList<ArrayList<String>> authors) {
        
		DAO dao = new DAO();
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        List<Author> authorList = new ArrayList<Author>();
	    for(int x=0; x<authors.size(); x++) {
	    	Author author = new Author(Long.parseLong(authors.get(x).get(0)));
	    	author.setName(authors.get(x).get(1));
	    	author.setUserId(currentUser.getId());
            author.setNameLc(author.getName().toLowerCase());
	    	authorList.add(author);
	    }
        
        dao.ofy().put(authorList);
        
    }

    public void persistDirectorList(ArrayList<ArrayList<String>> directors) {
        
		DAO dao = new DAO();
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        List<Director> directorList = new ArrayList<Director>();
	    for(int x=0; x<directors.size(); x++) {
	    	Director director = new Director(Long.parseLong(directors.get(x).get(0)));
	    	director.setName(directors.get(x).get(1));
	    	director.setUserId(currentUser.getId());
	    	director.setNameLc(director.getName().toLowerCase());
	    	directorList.add(director);
	    }
        
        dao.ofy().put(directorList);
        
    }

    //Method must be present in Service Interface and Async Interface
    public Book updateBook(Book book) {
    	
		DAO dao = new DAO();
		boolean titleUpdated = false;
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        if (book.getUserId() == null) {
            book.setUserId(currentUser.getId());
        }
        if (book.getCreateDate() == null) {
        	Date today_date = new Date();
            book.setCreateDate(today_date);
        } else {
        	//We know this is an existing record because the create date has a value.
        	//Now we will check to see if the title is being updated.
        	Book beforeUpdate = dao.ofy().get(Book.class, book.getId());
        	if (beforeUpdate.getTitle().equals(book.getTitle())) {
        		//do nothing
        	} else {
        		titleUpdated = true;
        	}
        }

        dao.ofy().put(book);
        assert book.getId() != null;
        Book fetched = dao.ofy().get(Book.class, book.getId());

        if (titleUpdated) {
        	Query<Quote> q = dao.ofy().query(Quote.class).filter("userId", currentUser.getId()).filter("bookId", book.getId());
        	for (Quote quote : q) {
        		quote.setBookTitle(book.getTitle());
        		dao.ofy().put(quote);
        	}
        }

        return fetched;

    }

    public void deleteBook(Book book) throws CannotDeleteException {
    	
    	DAO dao = new DAO();
        Query<Quote> q = dao.ofy().query(Quote.class).filter("bookId", book.getId());
        if (q.count() > 0) {
        	throw new CannotDeleteException();
        }
        
        dao.ofy().delete(book);

    }

    public Film updateFilm(Film film) {
    	
		DAO dao = new DAO();
		boolean titleUpdated = false;
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        if (film.getUserId() == null) {
            film.setUserId(currentUser.getId());
        }
        if (film.getCreateDate() == null) {
        	Date today_date = new Date();
            film.setCreateDate(today_date);
        } else {
        	//We know this is an existing record because the create date has a value.
        	//Now we will check to see if the title is being updated.
        	Film beforeUpdate = dao.ofy().get(Film.class, film.getId());
        	if (beforeUpdate.getTitle().equals(film.getTitle())) {
        		//do nothing
        	} else {
        		titleUpdated = true;
        	}
        }

        dao.ofy().put(film);
        assert film.getId() != null;
        Film fetched = dao.ofy().get(Film.class, film.getId());

        if (titleUpdated) {
        	Query<Quote> q = dao.ofy().query(Quote.class).filter("userId", currentUser.getId()).filter("filmId", film.getId());
        	for (Quote quote : q) {
        		quote.setFilmTitle(film.getTitle());
        		dao.ofy().put(quote);
        	}
        }

        return fetched;

    }

    public void deleteFilm(Film film) throws CannotDeleteException {
    	
    	DAO dao = new DAO();
        Query<Quote> q = dao.ofy().query(Quote.class).filter("filmId", film.getId());
        if (q.count() > 0) {
        	throw new CannotDeleteException();
        }
        
        dao.ofy().delete(film);

    }

    public void persistBookList(ArrayList<ArrayList<String>> books) {
        
		DAO dao = new DAO();
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        List<Book> bookList = new ArrayList<Book>();
	    for(int x=0; x<books.size(); x++) {
	    	Book book = new Book(Long.parseLong(books.get(x).get(0)));
	    	book.setTitle(books.get(x).get(1));
	    	book.setUserId(currentUser.getId());
	        book.setAuthorId(Long.parseLong(books.get(x).get(2)));
	        book.setGenre(books.get(x).get(3));
	        book.setRating(Byte.parseByte(books.get(x).get(4)));
	        if (books.get(x).get(5).equalsIgnoreCase("NULL")) {
	        	book.setNotes(null);
	        } else {
	        	book.setNotes(books.get(x).get(5));
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date work_date = null;
	        try {
	        	work_date = sdf.parse(books.get(x).get(6));
	        } catch (java.text.ParseException e) {
	        	System.out.println("ERROR: could not parse create date in string");
				e.printStackTrace();
			}
	        book.setCreateDate(work_date);
	        try {
	        	work_date = sdf.parse(books.get(x).get(7));
	        } catch (java.text.ParseException e) {
	        	System.out.println("ERROR: could not parse read date in string");
				e.printStackTrace();
			}
	        book.setReadDate(work_date);
	    	book.setAuthorName(books.get(x).get(8));

	        bookList.add(book);
	    }
        
        dao.ofy().put(bookList);
        
    }

    public void persistFilmList(ArrayList<ArrayList<String>> films) {
        
		DAO dao = new DAO();
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        List<Film> filmList = new ArrayList<Film>();
	    for(int x=0; x<films.size(); x++) {
	    	Film film = new Film(Long.parseLong(films.get(x).get(0)));
	    	film.setTitle(films.get(x).get(1));
	    	film.setUserId(currentUser.getId());
	    	film.setDirectorId(Long.parseLong(films.get(x).get(2)));
	    	film.setUserId(currentUser.getId());
	    	film.setYear(Integer.parseInt(films.get(x).get(3)));
	    	film.setRating(Byte.parseByte(films.get(x).get(4)));
	    	if (films.get(x).get(5).equalsIgnoreCase("NULL")) {
	    		film.setNotes(null);
	    	} else {
	    		film.setNotes(films.get(x).get(5));
	    	}
	    	if (films.get(x).get(6).equalsIgnoreCase("NULL")) {
	    		film.setStars(null);
	    	} else {
	    		film.setStars(films.get(x).get(6));
	    	}
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date work_date = null;
	        try {
	        	work_date = sdf.parse(films.get(x).get(7));
	        } catch (java.text.ParseException e) {
	        	System.out.println("ERROR: could not parse create date in string");
				e.printStackTrace();
			}
	        film.setCreateDate(work_date);
	        try {
	        	work_date = sdf.parse(films.get(x).get(8));
	        } catch (java.text.ParseException e) {
	        	System.out.println("ERROR: could not parse watch date in string");
				e.printStackTrace();
			}
	        film.setWatchDate(work_date);
	    	film.setDirectorName(films.get(x).get(9));

	        filmList.add(film);
	    }
        
        dao.ofy().put(filmList);
        
    }

    public void persistQuoteList(ArrayList<ArrayList<String>> quotes) {
        
		DAO dao = new DAO();
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
        
        List<Quote> quoteList = new ArrayList<Quote>();
	    for(int x=0; x<quotes.size(); x++) {
	    	Quote quote = new Quote(Long.parseLong(quotes.get(x).get(0)));
	    	if (Long.parseLong(quotes.get(x).get(1)) > 0 ) {
	    		quote.setBookId(Long.parseLong(quotes.get(x).get(1)));
	    	}
	    	if (Long.parseLong(quotes.get(x).get(2)) > 0 ) {
	    		quote.setFilmId(Long.parseLong(quotes.get(x).get(2)));
	    	}
	    	if (quotes.get(x).get(3).equalsIgnoreCase("NULL")) {
	    		quote.setCharacterName(null);
	    	} else {
	    		quote.setCharacterName(quotes.get(x).get(3));
	    	}
	    	quote.setQuoteText(quotes.get(x).get(4));
	    	if (quotes.get(x).get(5).equalsIgnoreCase("NULL")) {
	    		quote.setBookTitle(null);
	    	} else {
	    		quote.setBookTitle(quotes.get(x).get(5));
	    	}
	    	if (quotes.get(x).get(6).equalsIgnoreCase("NULL")) {
	    		quote.setFilmTitle(null);
	    	} else {
	    		quote.setFilmTitle(quotes.get(x).get(6));
	    	}
	    	quote.setUserId(currentUser.getId());
	    	quoteList.add(quote);
	    }
        
        dao.ofy().put(quoteList);
        
    }

    public void updateTopic(Topic topic) {
    	
    	DAO dao = new DAO();
        
        UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());

        if (topic.getUserId() == null) {
            topic.setUserId(currentUser.getId());
        }

        dao.ofy().put(topic);

        Query<Book> q1 = dao.ofy().query(Book.class).filter("userId", currentUser.getId()).filter("topicId", topic.getId());
        for (Book fetched : q1) {
            fetched.setTopicDesc(topic.getDescription());
            dao.ofy().put(fetched);
        }
        Query<Film> q2 = dao.ofy().query(Film.class).filter("userId", currentUser.getId()).filter("topicId", topic.getId());
        for (Film fetched : q2) {
            fetched.setTopicDesc(topic.getDescription());
            dao.ofy().put(fetched);
        }
        
    }

    public void deleteTopic(Topic topic) throws CannotDeleteException {
    	
    	DAO dao = new DAO();
        Query<Book> b = dao.ofy().query(Book.class).filter("topicId", topic.getId());
        if (b.count() > 0) {
        	throw new CannotDeleteException();
        }
        Query<Film> f = dao.ofy().query(Film.class).filter("topicId", topic.getId());
        if (f.count() > 0) {
        	throw new CannotDeleteException();
        }
        
    	dao.ofy().delete(topic);

    }

    public Quote updateQuote(Quote quote) {
    	
    	DAO dao = new DAO();
        
        if (quote.getUserId() == null) {
            UserAccount currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest().getSession());
            quote.setUserId(currentUser.getId());
        }

        dao.ofy().put(quote);
        assert quote.getId() != null;
        Quote fetched = dao.ofy().get(Quote.class, quote.getId());

        return fetched;
    }

    public void deleteQuote(Quote quote) {
    	
    	DAO dao = new DAO();
        
    	dao.ofy().delete(quote);

    }

    public void updateUser(UserAccount user) {
    	
    	DAO dao = new DAO();
    	
    	dao.ofy().put(user);

    }

    public void deleteUser(UserAccount user) throws CannotDeleteException {
    	
    	DAO dao = new DAO();
        Query<Author> a = dao.ofy().query(Author.class).filter("userId", user.getId());
        if (a.count() > 0) {
        	throw new CannotDeleteException();
        }
        Query<Director> d = dao.ofy().query(Director.class).filter("userId", user.getId());
        if (d.count() > 0) {
        	throw new CannotDeleteException();
        }
        Query<Topic> t = dao.ofy().query(Topic.class).filter("userId", user.getId());
        if (t.count() > 0) {
        	throw new CannotDeleteException();
        }
        
    	dao.ofy().delete(user);

    }

}
