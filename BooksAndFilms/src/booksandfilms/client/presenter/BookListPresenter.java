package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Media;
import booksandfilms.client.event.AuthorAddEvent;
import booksandfilms.client.event.AuthorUpdatedEvent;
import booksandfilms.client.event.AuthorUpdatedEventHandler;
import booksandfilms.client.event.BookAddEvent;
import booksandfilms.client.event.BookDeleteEvent;
import booksandfilms.client.event.BookDeleteEventHandler;
import booksandfilms.client.event.SaveBookMapEvent;
import booksandfilms.client.event.SetScrollHeightEvent;
import booksandfilms.client.event.SetScrollHeightEventHandler;
import booksandfilms.client.event.ShowAuthorPopupEvent;
import booksandfilms.client.event.ShowBookPopupEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class BookListPresenter implements Presenter {

	private Map<Long, Media> booksMap;
	private Map<Long, Media> authorsMap;
	private Map<Long, Media> topicsMap;
	private ArrayList<Book> books;
	private ArrayList<Author> authors;
	private ArrayList<Book> someBooks = new ArrayList<Book>();
	private ArrayList<Book> booksByAuthor = new ArrayList<Book>();
	private Author currentAuthor = null;
	private Long filterId = null;
	private int scrollHeight;
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		void setData(List<Book> books);
		void setAuthors(List<Author> authors);
		HasChangeHandlers getAuthorFilter();
		String getAuthorValue();
		void showAddBook();
		void hideAddBook();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		int[] getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		boolean getAdmin();
		Widget asWidget();
		HasClickHandlers getSortTitleButton();
		HasClickHandlers getSortAuthorButton();
		HasClickHandlers getSortRatingButton();
		HasClickHandlers getSortGenreButton();
		HasClickHandlers getSortReadDateButton();
		void setScrollHeight(int parentHeight);
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	protected List<Author> allAuthors;
	protected List<Author> someAuthors;
	//This value is also mentioned in BookListView
	private static final int authorColumnNumber = 2;

	public BookListPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Media> booksMap, Map<Long, Media> authorsMap, int scrollHeight) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.booksMap = booksMap;
		this.authorsMap = authorsMap;
		this.scrollHeight = scrollHeight;
		bind();
	}
	
	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new BookAddEvent(currentAuthor));
			}
		});
		display.getSortTitleButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Book> cp = Book.getComparator(Book.SortParameter.TITLE_ASCENDING);
				Collections.sort(books, cp);
				display.setData(books);
			}
		});
		display.getSortAuthorButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Book> cp = Book.getComparator(Book.SortParameter.AUTHOR_ASCENDING,Book.SortParameter.TITLE_ASCENDING);
				Collections.sort(books, cp);
				display.setData(books);
			}
		});
		display.getSortRatingButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Book> cp = Book.getComparator(Book.SortParameter.RATING_DESCENDING,Book.SortParameter.TITLE_ASCENDING);
				Collections.sort(books, cp);
				display.setData(books);
			}
		});
		display.getSortGenreButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Book> cp = Book.getComparator(Book.SortParameter.GENRE_ASCENDING,Book.SortParameter.RATING_DESCENDING,Book.SortParameter.TITLE_ASCENDING);
				Collections.sort(books, cp);
				display.setData(books);
			}
		});
		display.getSortReadDateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Book> cp = Book.getComparator(Book.SortParameter.READ_DESCENDING,Book.SortParameter.TITLE_ASCENDING);
				Collections.sort(books, cp);
				display.setData(books);
			}
		});
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchBoxChange();
				}
			}
		});
		display.getAuthorFilter().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				authorChange();
			}
		});
		
		
		if (display.getList() != null)
			display.getList().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int[] rowValues = new int[2];
					rowValues = display.getClickedRow(event);
					int selectedPropertyButtonRow = rowValues[0];
					if (selectedPropertyButtonRow >= 0) {
						int columnNumber = rowValues[1];
			            ClickPoint point = display.getClickedPoint(event);
			            Book book = someBooks.get(selectedPropertyButtonRow);
						if (columnNumber == authorColumnNumber) {
							Author author = (Author) authorsMap.get(book.getAuthorId());
				            eventBus.fireEvent(new ShowAuthorPopupEvent(author, point));
						} else {
				            eventBus.fireEvent(new ShowBookPopupEvent(book, point, allAuthors, someAuthors));
						}
						
					}
				}
			});
		// Listen to events

		eventBus.addHandler(BookDeleteEvent.TYPE, new BookDeleteEventHandler() {
			@Override public void onDeleteBook(BookDeleteEvent event) {
				booksMap.remove(event.getBook().getId());
				books = sortResults(booksMap);
				filterBooks();
			}
		});

		eventBus.addHandler(AuthorUpdatedEvent.TYPE, new AuthorUpdatedEventHandler() {
			public void onUpdateAuthor(AuthorUpdatedEvent event) {
				display.setAuthors(authors);
			}
		});

		eventBus.addHandler(SetScrollHeightEvent.TYPE, new SetScrollHeightEventHandler() {
			public void onSetHeight(SetScrollHeightEvent event) {
				display.setScrollHeight(event.getScrollHeight());
			}
		});

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		if (booksMap == null) {
			Log.debug("Books Map is null, therefore hit the Datastore");
			fetchBookData();
		} else {
			Log.debug("Books Map has value, re-paint the books array.");
			books = sortResults(booksMap);
			authors = sortAuthors(authorsMap);
			filterBooks();
			display.setAuthors(authors);
			display.hideAddBook();
			display.setScrollHeight(scrollHeight);
		}
	}

	private void fetchBookData() {
		new RPCCall<ArrayList<Map<Long, Media>>>() {
			@Override protected void callService(AsyncCallback<ArrayList<Map<Long, Media>>> cb) {
				rpcService.getBookData(cb);
			}
			
			public void onSuccess(ArrayList<Map<Long, Media>> result) {
				booksMap = result.get(0);
				books = sortResults(booksMap);
				authorsMap = result.get(1);
				authors = sortAuthors(authorsMap);
				topicsMap = result.get(2);
				eventBus.fireEvent(new SaveBookMapEvent(booksMap, authorsMap, topicsMap));
				filterBooks();
				display.setAuthors(authors);
				display.hideAddBook();
				display.setScrollHeight(BooksAndFilms.get().getMainPanel().getOffsetHeight());
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching book summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void filterBooks() {
		booksByAuthor.clear();
		someBooks.clear();
		if (filterId == null) {
			if (books != null) {
				for (Book b : books) {
					booksByAuthor.add(b);
					someBooks.add(b);
				}
			}
		} else {
			for (Book b : books) {
				if (b.getAuthorId().equals(filterId)) {
					try {
						booksByAuthor.add(b);
						someBooks.add(b);
					} catch (Exception e) {
			        	System.out.println("ERROR: could not add book to list");
						e.printStackTrace();
					}
				}
			}
		}
		display.setData(booksByAuthor);
	}

	private void searchBoxChange() {
		List<Book> tempBooks = new ArrayList<Book>();
		tempBooks = booksByAuthor;
		String searchString = display.getSearchValue();
		someBooks.clear();
		if (searchString.isEmpty()) {
			display.setData(tempBooks);
			for (Book b : tempBooks) {
				someBooks.add(b);
			}
		} else {
			for (Book b : tempBooks) {
				if (b.getTitle().toUpperCase().startsWith(searchString.toUpperCase()))
					someBooks.add(b);
			}
			display.setData(someBooks);
		}
	}
	
	private void authorChange() {
		if (display.getAuthorValue()== null) {
			Log.debug("Author is null, select all");
			display.hideAddBook();
			filterId = null;
			filterBooks();
		} else if (display.getAuthorValue().equalsIgnoreCase("add new...")) {
			Log.debug("Add a new author");
			eventBus.fireEvent(new AuthorAddEvent());
		} else {
			filterId = Long.valueOf(display.getAuthorValue());
			display.showAddBook();
			currentAuthor = (Author) authorsMap.get(filterId);
			Log.debug("Author changed value = "+display.getAuthorValue());
			filterBooks();
		}
	}
	
	private ArrayList<Book> sortResults(Map<Long, Media> unSorted) {
		ArrayList<Book> sorted = new ArrayList<Book>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Book book = (Book) thisEntry.getValue();
			sorted.add(book);
		}
		Comparator<Book> cp = Book.getComparator(Book.SortParameter.READ_DESCENDING, Book.SortParameter.RATING_DESCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
	private ArrayList<Author> sortAuthors(Map<Long, Media> unSorted) {
		ArrayList<Author> sorted = new ArrayList<Author>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Author author = (Author) thisEntry.getValue();
			sorted.add(author);
		}
		Comparator<Author> cp = Author.getComparator(Author.SortParameter.NAME_ASCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
}
