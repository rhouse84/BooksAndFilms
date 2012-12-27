package booksandfilms.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.event.AuthorAddEvent;
import booksandfilms.client.event.AuthorAddEventHandler;
import booksandfilms.client.event.AuthorDeleteEvent;
import booksandfilms.client.event.AuthorDeleteEventHandler;
import booksandfilms.client.event.AuthorEditCancelledEvent;
import booksandfilms.client.event.AuthorEditCancelledEventHandler;
import booksandfilms.client.event.AuthorEditEvent;
import booksandfilms.client.event.AuthorEditEventHandler;
import booksandfilms.client.event.AuthorUpdatedEvent;
import booksandfilms.client.event.AuthorUpdatedEventHandler;
import booksandfilms.client.event.BookAddEvent;
import booksandfilms.client.event.BookAddEventHandler;
import booksandfilms.client.event.BookDeleteEvent;
import booksandfilms.client.event.BookDeleteEventHandler;
import booksandfilms.client.event.BookEditCancelledEvent;
import booksandfilms.client.event.BookEditCancelledEventHandler;
import booksandfilms.client.event.BookEditEvent;
import booksandfilms.client.event.BookEditEventHandler;
import booksandfilms.client.event.BookUpdatedEvent;
import booksandfilms.client.event.BookUpdatedEventHandler;
import booksandfilms.client.event.DirectorAddEvent;
import booksandfilms.client.event.DirectorAddEventHandler;
import booksandfilms.client.event.DirectorDeleteEvent;
import booksandfilms.client.event.DirectorDeleteEventHandler;
import booksandfilms.client.event.DirectorEditCancelledEvent;
import booksandfilms.client.event.DirectorEditCancelledEventHandler;
import booksandfilms.client.event.DirectorEditEvent;
import booksandfilms.client.event.DirectorEditEventHandler;
import booksandfilms.client.event.DirectorUpdatedEvent;
import booksandfilms.client.event.DirectorUpdatedEventHandler;
import booksandfilms.client.event.FilmAddEvent;
import booksandfilms.client.event.FilmAddEventHandler;
import booksandfilms.client.event.FilmDeleteEvent;
import booksandfilms.client.event.FilmDeleteEventHandler;
import booksandfilms.client.event.FilmEditCancelledEvent;
import booksandfilms.client.event.FilmEditCancelledEventHandler;
import booksandfilms.client.event.FilmEditEvent;
import booksandfilms.client.event.FilmEditEventHandler;
import booksandfilms.client.event.FilmUpdatedEvent;
import booksandfilms.client.event.FilmUpdatedEventHandler;
import booksandfilms.client.event.GetQuoteMapEvent;
import booksandfilms.client.event.GetQuoteMapEventHandler;
import booksandfilms.client.event.LogoutEvent;
import booksandfilms.client.event.LogoutEventHandler;
import booksandfilms.client.event.QuoteDeleteEvent;
import booksandfilms.client.event.QuoteDeleteEventHandler;
import booksandfilms.client.event.QuoteUpdatedEvent;
import booksandfilms.client.event.QuoteUpdatedEventHandler;
import booksandfilms.client.event.ReturnQuoteMapEvent;
import booksandfilms.client.event.SaveBookMapEvent;
import booksandfilms.client.event.SaveBookMapEventHandler;
import booksandfilms.client.event.SaveFilmMapEvent;
import booksandfilms.client.event.SaveFilmMapEventHandler;
import booksandfilms.client.event.SaveQuoteMapEvent;
import booksandfilms.client.event.SaveQuoteMapEventHandler;
import booksandfilms.client.event.SetScrollHeightEvent;
import booksandfilms.client.event.SetScrollHeightEventHandler;
import booksandfilms.client.event.ShowAuthorPopupEvent;
import booksandfilms.client.event.ShowAuthorPopupEventHandler;
import booksandfilms.client.event.ShowBookPopupEvent;
import booksandfilms.client.event.ShowBookPopupEventHandler;
import booksandfilms.client.event.ShowDirectorPopupEvent;
import booksandfilms.client.event.ShowDirectorPopupEventHandler;
import booksandfilms.client.event.ShowFilmPopupEvent;
import booksandfilms.client.event.ShowFilmPopupEventHandler;
import booksandfilms.client.event.ShowMediaGetEvent;
import booksandfilms.client.event.ShowMediaGetEventHandler;
import booksandfilms.client.event.ShowTopicListEvent;
import booksandfilms.client.event.ShowTopicListEventHandler;
import booksandfilms.client.event.ShowTopicPopupEvent;
import booksandfilms.client.event.ShowTopicPopupEventHandler;
import booksandfilms.client.event.ShowUserListEvent;
import booksandfilms.client.event.ShowUserListEventHandler;
import booksandfilms.client.event.ShowUserPopupEvent;
import booksandfilms.client.event.ShowUserPopupEventHandler;
import booksandfilms.client.event.TopicAddEvent;
import booksandfilms.client.event.TopicAddEventHandler;
import booksandfilms.client.event.TopicEditCancelledEvent;
import booksandfilms.client.event.TopicEditCancelledEventHandler;
import booksandfilms.client.event.TopicEditEvent;
import booksandfilms.client.event.TopicEditEventHandler;
import booksandfilms.client.event.TopicUpdatedEvent;
import booksandfilms.client.event.TopicUpdatedEventHandler;
import booksandfilms.client.event.UserAddEvent;
import booksandfilms.client.event.UserAddEventHandler;
import booksandfilms.client.event.UserEditCancelledEvent;
import booksandfilms.client.event.UserEditCancelledEventHandler;
import booksandfilms.client.event.UserEditEvent;
import booksandfilms.client.event.UserEditEventHandler;
import booksandfilms.client.event.UserUpdatedEvent;
import booksandfilms.client.event.UserUpdatedEventHandler;
import booksandfilms.client.presenter.AuthorEditPresenter;
import booksandfilms.client.presenter.AuthorPopupPresenter;
import booksandfilms.client.presenter.BookEditPresenter;
import booksandfilms.client.presenter.BookListPresenter;
import booksandfilms.client.presenter.BookPopupPresenter;
import booksandfilms.client.presenter.DirectorEditPresenter;
import booksandfilms.client.presenter.DirectorPopupPresenter;
import booksandfilms.client.presenter.FilmEditPresenter;
import booksandfilms.client.presenter.FilmListPresenter;
import booksandfilms.client.presenter.FilmPopupPresenter;
import booksandfilms.client.presenter.MediaGetPresenter;
import booksandfilms.client.presenter.Presenter;
import booksandfilms.client.presenter.TopicEditPresenter;
import booksandfilms.client.presenter.TopicListPresenter;
import booksandfilms.client.presenter.TopicPopupPresenter;
import booksandfilms.client.presenter.UserEditPresenter;
import booksandfilms.client.presenter.UserListPresenter;
import booksandfilms.client.presenter.UserPopupPresenter;
import booksandfilms.client.view.AuthorEditView;
import booksandfilms.client.view.AuthorPopupView;
import booksandfilms.client.view.BookEditView;
import booksandfilms.client.view.BookListView;
import booksandfilms.client.view.BookPopupView;
import booksandfilms.client.view.DirectorEditView;
import booksandfilms.client.view.DirectorPopupView;
import booksandfilms.client.view.FilmEditView;
import booksandfilms.client.view.FilmListView;
import booksandfilms.client.view.FilmPopupView;
import booksandfilms.client.view.MediaGetView;
import booksandfilms.client.view.TopicEditView;
import booksandfilms.client.view.TopicListView;
import booksandfilms.client.view.TopicPopupView;
import booksandfilms.client.view.UserEditView;
import booksandfilms.client.view.UserListView;
import booksandfilms.client.view.UserPopupView;

public class AppController implements ValueChangeHandler<String> {
	private final SimpleEventBus eventBus;
	private final QueryServiceAsync queryService;
	private final PersistentServiceAsync persistentService;
	private Long currentAuthorId;
	private Long currentDirectorId;
	private Long currentBookId;
	private Long currentFilmId;
	private Long currentUserId;
	private Long currentTopicId;
	private Author currentAuthor;
	private Director currentDirector;
	private Map<Long, Media> booksMap;
	private Map<Long, Media> authorsMap;
	private Map<Long, Media> filmsMap;
	private Map<Long, Media> directorsMap;
	private Map<Long, Media> quotesMap;
	private Map<Long, Media> topicsMap;
	private int scrollHeight;

	public AppController(PersistentServiceAsync persistentService, QueryServiceAsync queryService, SimpleEventBus eventBus) {
		this.eventBus = eventBus;
		this.queryService = queryService;
		this.persistentService = persistentService;
		bind();
	}
	
	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(GetQuoteMapEvent.TYPE, new GetQuoteMapEventHandler() {
			public void onGetQuoteMap(GetQuoteMapEvent event) {
				doGetQuoteMap(event.getMedia());
			}
		});

		eventBus.addHandler(AuthorAddEvent.TYPE, new AuthorAddEventHandler() {
			public void onAddAuthor(AuthorAddEvent event) {
				doAddNewAuthor();
			}
		});

		eventBus.addHandler(BookAddEvent.TYPE, new BookAddEventHandler() {
			public void onAddBook(BookAddEvent event) {
				doAddNewBook(event.getAuthor());
			}
		});

		eventBus.addHandler(DirectorAddEvent.TYPE, new DirectorAddEventHandler() {
			public void onAddDirector(DirectorAddEvent event) {
				doAddNewDirector();
			}
		});

		eventBus.addHandler(FilmAddEvent.TYPE, new FilmAddEventHandler() {
			public void onAddFilm(FilmAddEvent event) {
				doAddNewFilm(event.getDirector());
			}
		});

		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			@Override public void onLogout(LogoutEvent event) {
				doLogout();
			}
		});

		eventBus.addHandler(ShowAuthorPopupEvent.TYPE, new ShowAuthorPopupEventHandler() {
			public void onShowAuthorPopup(ShowAuthorPopupEvent event) {
				AuthorPopupPresenter authorPopupPresenter = new AuthorPopupPresenter(persistentService, eventBus,
						new AuthorPopupView(event.getAuthor().getName(), event.getClickPoint()), event.getAuthor());
				authorPopupPresenter.go();
			}
		});

		eventBus.addHandler(ShowDirectorPopupEvent.TYPE, new ShowDirectorPopupEventHandler() {
			public void onShowDirectorPopup(ShowDirectorPopupEvent event) {
				DirectorPopupPresenter directorPopupPresenter = new DirectorPopupPresenter(persistentService, eventBus,
						new DirectorPopupView(event.getDirector().getName(), event.getClickPoint()), event.getDirector());
				directorPopupPresenter.go();
			}
		});

		eventBus.addHandler(ShowBookPopupEvent.TYPE, new ShowBookPopupEventHandler() {
			public void onShowBookPopup(ShowBookPopupEvent event) {
				BookPopupPresenter bookPopupPresenter = new BookPopupPresenter(persistentService, eventBus,
						new BookPopupView(event.getBook().getTitle(), event.getClickPoint()), event.getBook());
				bookPopupPresenter.go();
			}
		});

		eventBus.addHandler(ShowFilmPopupEvent.TYPE, new ShowFilmPopupEventHandler() {
			public void onShowFilmPopup(ShowFilmPopupEvent event) {
				FilmPopupPresenter filmPopupPresenter = new FilmPopupPresenter(persistentService, eventBus,
						new FilmPopupView(event.getFilm().getTitle(), event.getClickPoint()), event.getFilm());
				filmPopupPresenter.go();
			}
		});

		eventBus.addHandler(AuthorEditEvent.TYPE, new AuthorEditEventHandler() {
			public void onEditAuthor(AuthorEditEvent event) {
				doEditAuthor(event.getId());
			}
		});

		eventBus.addHandler(DirectorEditEvent.TYPE, new DirectorEditEventHandler() {
			public void onEditDirector(DirectorEditEvent event) {
				doEditDirector(event.getId());
			}
		});

		eventBus.addHandler(BookEditEvent.TYPE, new BookEditEventHandler() {
			public void onEditBook(BookEditEvent event) {
				doEditBook(event.getId());
			}
		});

		eventBus.addHandler(FilmEditEvent.TYPE, new FilmEditEventHandler() {
			public void onEditFilm(FilmEditEvent event) {
				doEditFilm(event.getId());
			}
		});

		eventBus.addHandler(AuthorEditCancelledEvent.TYPE, new AuthorEditCancelledEventHandler() {
			public void onEditCancelAuthor(AuthorEditCancelledEvent event) {
				doEditAuthorCancelled();
			}
		});

		eventBus.addHandler(AuthorUpdatedEvent.TYPE, new AuthorUpdatedEventHandler() {
			public void onUpdateAuthor(AuthorUpdatedEvent event) {
				doAuthorUpdated(event.getAuthor());
			}
		});

		eventBus.addHandler(AuthorDeleteEvent.TYPE, new AuthorDeleteEventHandler() {
			@Override public void onDeleteAuthor(AuthorDeleteEvent event) {
				doAuthorDeleted(event.getAuthor());
			}
		});

		eventBus.addHandler(DirectorEditCancelledEvent.TYPE, new DirectorEditCancelledEventHandler() {
			public void onEditCancelDirector(DirectorEditCancelledEvent event) {
				doEditDirectorCancelled();
			}
		});

		eventBus.addHandler(DirectorUpdatedEvent.TYPE, new DirectorUpdatedEventHandler() {
			public void onUpdateDirector(DirectorUpdatedEvent event) {
				doDirectorUpdated(event.getDirector());
			}
		});

		eventBus.addHandler(DirectorDeleteEvent.TYPE, new DirectorDeleteEventHandler() {
			@Override public void onDeleteDirector(DirectorDeleteEvent event) {
				doDirectorDeleted(event.getDirector());
			}
		});

		eventBus.addHandler(BookEditCancelledEvent.TYPE, new BookEditCancelledEventHandler() {
			public void onEditCancelBook(BookEditCancelledEvent event) {
				doEditBookCancelled();
			}
		});

		eventBus.addHandler(BookUpdatedEvent.TYPE, new BookUpdatedEventHandler() {
			public void onUpdateBook(BookUpdatedEvent event) {
				doBookUpdated(event.getBook());
			}
		});

		eventBus.addHandler(BookDeleteEvent.TYPE, new BookDeleteEventHandler() {
			@Override public void onDeleteBook(BookDeleteEvent event) {
				doBookDeleted(event.getBook());
			}
		});

		eventBus.addHandler(FilmEditCancelledEvent.TYPE, new FilmEditCancelledEventHandler() {
			public void onEditCancelFilm(FilmEditCancelledEvent event) {
				doEditFilmCancelled();
			}
		});

		eventBus.addHandler(FilmUpdatedEvent.TYPE, new FilmUpdatedEventHandler() {
			public void onUpdateFilm(FilmUpdatedEvent event) {
				doFilmUpdated(event.getFilm());
			}
		});

		eventBus.addHandler(FilmDeleteEvent.TYPE, new FilmDeleteEventHandler() {
			@Override public void onDeleteFilm(FilmDeleteEvent event) {
				doFilmDeleted(event.getFilm());
			}
		});

		eventBus.addHandler(QuoteUpdatedEvent.TYPE, new QuoteUpdatedEventHandler() {
			public void onUpdateQuote(QuoteUpdatedEvent event) {
				doQuoteUpdated(event.getQuote());
			}
		});

		eventBus.addHandler(QuoteDeleteEvent.TYPE, new QuoteDeleteEventHandler() {
			public void onDeleteQuote(QuoteDeleteEvent event) {
				doQuoteDeleted(event.getQuote());
			}
		});

		eventBus.addHandler(ShowUserListEvent.TYPE, new ShowUserListEventHandler() {
			public void onShowUserList(ShowUserListEvent event) {
				doShowUserList();
			}
		});

		eventBus.addHandler(UserAddEvent.TYPE, new UserAddEventHandler() {
			public void onAddUser(UserAddEvent event) {
				doAddNewUser();
			}
		});

		eventBus.addHandler(ShowUserPopupEvent.TYPE, new ShowUserPopupEventHandler() {
			public void onShowUserPopup(ShowUserPopupEvent event) {
				UserPopupPresenter userPopupPresenter = new UserPopupPresenter(persistentService, eventBus,
						new UserPopupView(event.getUser().getName(), event.getClickPoint()), event.getUser());
				userPopupPresenter.go();
			}
		});

		eventBus.addHandler(UserEditEvent.TYPE, new UserEditEventHandler() {
			public void onEditUser(UserEditEvent event) {
				doEditUser(event.getId());
			}
		});

		eventBus.addHandler(UserEditCancelledEvent.TYPE, new UserEditCancelledEventHandler() {
			public void onEditCancelUser(UserEditCancelledEvent event) {
				doEditUserCancelled();
			}
		});

		eventBus.addHandler(UserUpdatedEvent.TYPE, new UserUpdatedEventHandler() {
			public void onUpdateUser(UserUpdatedEvent event) {
				doUserUpdated();
			}
		});

		eventBus.addHandler(ShowTopicListEvent.TYPE, new ShowTopicListEventHandler() {
			public void onShowTopicList(ShowTopicListEvent event) {
				doShowTopicList();
			}
		});

		eventBus.addHandler(TopicAddEvent.TYPE, new TopicAddEventHandler() {
			public void onAddTopic(TopicAddEvent event) {
				doAddNewTopic();
			}
		});

		eventBus.addHandler(ShowTopicPopupEvent.TYPE, new ShowTopicPopupEventHandler() {
			public void onShowTopicPopup(ShowTopicPopupEvent event) {
				TopicPopupPresenter topicPopupPresenter = new TopicPopupPresenter(persistentService, eventBus,
						new TopicPopupView(event.getTopic().getDescription(), event.getClickPoint()), event.getTopic());
				topicPopupPresenter.go();
			}
		});

		eventBus.addHandler(TopicEditEvent.TYPE, new TopicEditEventHandler() {
			public void onEditTopic(TopicEditEvent event) {
				doEditTopic(event.getId());
			}
		});

		eventBus.addHandler(TopicEditCancelledEvent.TYPE, new TopicEditCancelledEventHandler() {
			public void onEditCancelTopic(TopicEditCancelledEvent event) {
				doEditTopicCancelled();
			}
		});

		eventBus.addHandler(TopicUpdatedEvent.TYPE, new TopicUpdatedEventHandler() {
			public void onUpdateTopic(TopicUpdatedEvent event) {
				doTopicUpdated();
			}
		});

		eventBus.addHandler(SaveBookMapEvent.TYPE, new SaveBookMapEventHandler() {
			public void onSaveBookMap(SaveBookMapEvent event) {
				booksMap = event.getBooksMap();
				authorsMap = event.getAuthorsMap();
				topicsMap = event.getTopicsMap();
			}
		});

		eventBus.addHandler(SaveFilmMapEvent.TYPE, new SaveFilmMapEventHandler() {
			public void onSaveFilmMap(SaveFilmMapEvent event) {
				filmsMap = event.getFilmsMap();
				directorsMap = event.getDirectorsMap();
			}
		});

		eventBus.addHandler(SaveQuoteMapEvent.TYPE, new SaveQuoteMapEventHandler() {
			public void onSaveQuoteMap(SaveQuoteMapEvent event) {
				quotesMap = event.getQuotesMap();
			}
		});

		eventBus.addHandler(SetScrollHeightEvent.TYPE, new SetScrollHeightEventHandler() {
			public void onSetHeight(SetScrollHeightEvent event) {
				scrollHeight = event.getScrollHeight();
			}
		});

		eventBus.addHandler(ShowMediaGetEvent.TYPE, new ShowMediaGetEventHandler() {
			public void onShowMediaGet(ShowMediaGetEvent event) {
				MediaGetPresenter mediaGetPresenter = new MediaGetPresenter(queryService, eventBus,
						new MediaGetView(event.getTopLeft()), event.getMediaName());
				mediaGetPresenter.go();
			}
		});

	}
	
	private void doGetQuoteMap(Media media) {
		Map<Long, Media> quoteSubsetMap = new HashMap<Long, Media>();
		boolean match = false;
		Iterator<Entry<Long, Media>> entries = quotesMap.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Quote quote = (Quote) thisEntry.getValue();
			if (media instanceof Book) {
				Book book = (Book) media;
				if (quote.getBookId() != null) {
					if (quote.getBookId().equals(book.getId())) {
						match = true;
					}
				}
			} else if (media instanceof Film) {
				Film film = (Film) media;
				if (quote.getFilmId() != null) {
					if (quote.getFilmId().equals(film.getId())) {
						match = true;
					}
				}
			}
			if (match) {
				quoteSubsetMap.put(quote.getId(), quote);
			}
			match = false;
		}
		
		eventBus.fireEvent(new ReturnQuoteMapEvent(quoteSubsetMap));
	}

	private void doAddNewAuthor() {
		History.newItem("authoradd");
	}

	private void doAddNewDirector() {
		History.newItem("directoradd");
	}

	private void doEditAuthor(Long id) {
		currentAuthorId = id;
		History.newItem("authoredit");
	}

	private void doEditDirector(Long id) {
		currentDirectorId = id;
		History.newItem("directoredit");
	}

	private void doAddNewBook(Author parmAuthor) {
		currentAuthor = parmAuthor;
		History.newItem("bookadd");
	}

	private void doAddNewFilm(Director parmDirector) {
		currentDirector = parmDirector;
		History.newItem("filmadd");
	}

	private void doEditBook(Long id) {
		currentBookId = id;
		History.newItem("bookedit");
	}

	private void doEditFilm(Long id) {
		currentFilmId = id;
		History.newItem("filmedit");
	}

	private void doEditAuthorCancelled() {
		History.newItem("list");
	}
	
	private void doEditDirectorCancelled() {
		History.newItem("filmlist");
	}
	
	private void doAuthorUpdated(Author author) {
		authorsMap.put(author.getId(), author);
		//We need to update the author name in each record of the booksMap
		@SuppressWarnings("rawtypes")
		Iterator iterator = booksMap.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Book book = (Book) thisEntry.getValue();
			if (book.getAuthorId() == author.getId()) {
				book.setAuthorName(author.getName());
				booksMap.put(book.getId(), book);
			}
        }

		History.newItem("list");
	}
	
	private void doAuthorDeleted(Author author) {
		authorsMap.remove(author.getId());
	}
	
	private void doDirectorUpdated(Director director) {
		directorsMap.put(director.getId(), director);
		//We need to update the director name in each record of the filmsMap
		@SuppressWarnings("rawtypes")
		Iterator iterator = filmsMap.entrySet().iterator();
        while(iterator.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) iterator.next();
			Film film = (Film) thisEntry.getValue();
			if (film.getDirectorId() == director.getId()) {
				film.setDirectorName(director.getName());
				filmsMap.put(film.getId(), film);
			}
        }

		History.newItem("filmlist");
	}
	
	private void doDirectorDeleted(Director director) {
		directorsMap.remove(director.getId());
	}
	
	private void doEditBookCancelled() {
		History.newItem("list");
	}
	
	private void doEditFilmCancelled() {
		History.newItem("filmlist");
	}
	
	private void doBookUpdated(Book book) {
		booksMap.put(book.getId(), book);
		History.newItem("list");
	}
	
	private void doBookDeleted(Book book) {
		booksMap.remove(book.getId());
	}
	
	private void doFilmUpdated(Film film) {
		filmsMap.put(film.getId(), film);
		History.newItem("filmlist");
	}
	
	private void doFilmDeleted(Film film) {
		filmsMap.remove(film.getId());
	}
	
	private void doQuoteUpdated(Quote quote) {
		quotesMap.put(quote.getId(), quote);
	}
	
	private void doQuoteDeleted(Quote quote) {
		quotesMap.remove(quote.getId());
	}
	
	private void doLogout() {
		History.newItem("login");
	}

	private void doShowUserList() {
		History.newItem("userlist");
	}

	private void doAddNewUser() {
		History.newItem("useradd");
	}

	private void doEditUser(Long id) {
		currentUserId = id;
		History.newItem("useredit");
	}

	private void doEditUserCancelled() {
		History.newItem("userlist");
	}
	
	private void doUserUpdated() {
		History.newItem("userlist");
	}
	
	private void doShowTopicList() {
		History.newItem("topiclist");
	}

	private void doAddNewTopic() {
		History.newItem("topicadd");
	}

	private void doEditTopic(Long id) {
		currentTopicId = id;
		History.newItem("topicedit");
	}

	private void doEditTopicCancelled() {
		History.newItem("topiclist");
	}
	
	private void doTopicUpdated() {
		History.newItem("topiclist");
	}
	
	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
		if (token != null) {
			Presenter presenter = null;
			if (token.equals("list")) {
				presenter = new BookListPresenter(queryService, eventBus, new BookListView(), booksMap, authorsMap, scrollHeight);
				presenter.go(BooksAndFilms.get().getMainPanel());
					
				return;
			} else if (token.equals("filmlist")) {
				presenter = new FilmListPresenter(queryService, eventBus, new FilmListView(), filmsMap, directorsMap, scrollHeight);
				presenter.go(BooksAndFilms.get().getMainFilmPanel());
				
				return;
			} else if (token.equals("authoradd")) {
				presenter = new AuthorEditPresenter(queryService, persistentService, eventBus, new AuthorEditView(), null);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("directoradd")) {
				presenter = new DirectorEditPresenter(queryService, persistentService, eventBus, new DirectorEditView(), null);
				presenter.go(BooksAndFilms.get().getMainFilmPanel());
				
				return;
			} else if (token.equals("bookadd")) {
				presenter = new BookEditPresenter(queryService, persistentService, eventBus, new BookEditView(), currentAuthor, null, topicsMap);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("filmadd")) {
				presenter = new FilmEditPresenter(queryService, persistentService, eventBus, new FilmEditView(), currentDirector, null, topicsMap);
				presenter.go(BooksAndFilms.get().getMainFilmPanel());
				
				return;
			} else if (token.equals("login")) {
				BooksAndFilms.get().showLoginView();
				
				return;
			} else if (token.equals("authoredit")) {
				presenter = new AuthorEditPresenter(queryService, persistentService, eventBus, new AuthorEditView(), currentAuthorId);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("directoredit")) {
				presenter = new DirectorEditPresenter(queryService, persistentService, eventBus, new DirectorEditView(), currentDirectorId);
				presenter.go(BooksAndFilms.get().getMainFilmPanel());
				
				return;
			} else if (token.equals("bookedit")) {
				presenter = new BookEditPresenter(queryService, persistentService, eventBus, new BookEditView(), null, currentBookId, topicsMap);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("filmedit")) {
				presenter = new FilmEditPresenter(queryService, persistentService, eventBus, new FilmEditView(), null, currentFilmId, topicsMap);
				presenter.go(BooksAndFilms.get().getMainFilmPanel());
				
				return;
			} else if (token.equals("userlist")) {
				presenter = new UserListPresenter(queryService, eventBus, new UserListView());
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("useradd")) {
				presenter = new UserEditPresenter(queryService, persistentService, eventBus, new UserEditView(), null);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("useredit")) {
				presenter = new UserEditPresenter(queryService, persistentService, eventBus, new UserEditView(), currentUserId);
				presenter.go(BooksAndFilms.get().getMainPanel());
				
				return;
			} else if (token.equals("topiclist")) {
				presenter = new TopicListPresenter(queryService, eventBus, new TopicListView());
				presenter.go(BooksAndFilms.get().getMainPanel());
			
				return;
			} else if (token.equals("topicadd")) {
				presenter = new TopicEditPresenter(queryService, persistentService, eventBus, new TopicEditView(), null);
				presenter.go(BooksAndFilms.get().getMainPanel());
			
				return;
			} else if (token.equals("topicedit")) {
				presenter = new TopicEditPresenter(queryService, persistentService, eventBus, new TopicEditView(), currentTopicId);
				presenter.go(BooksAndFilms.get().getMainPanel());
			
				return;
			}
			
		}
	}

}
