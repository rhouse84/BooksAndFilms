package booksandfilms.server;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.entities.UserAccount;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {
    static {
        ObjectifyService.register(Author.class);
        ObjectifyService.register(Book.class);
        ObjectifyService.register(Director.class);
        ObjectifyService.register(Film.class);
        ObjectifyService.register(Quote.class);
        ObjectifyService.register(Topic.class);
        ObjectifyService.register(UserAccount.class);
    }
    
}
