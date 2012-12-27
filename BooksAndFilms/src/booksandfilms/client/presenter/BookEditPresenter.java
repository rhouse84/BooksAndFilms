package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import com.allen_sauer.gwt.log.client.Log;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Genre;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.event.BookEditCancelledEvent;
import booksandfilms.client.event.BookUpdatedEvent;
import booksandfilms.client.event.GetQuoteMapEvent;
import booksandfilms.client.event.QuoteAddEvent;
import booksandfilms.client.event.QuoteAddEventHandler;
import booksandfilms.client.event.QuoteDeleteEvent;
import booksandfilms.client.event.QuoteDeleteEventHandler;
import booksandfilms.client.event.QuoteEditCancelledEvent;
import booksandfilms.client.event.QuoteEditCancelledEventHandler;
import booksandfilms.client.event.QuoteEditEvent;
import booksandfilms.client.event.QuoteEditEventHandler;
import booksandfilms.client.event.QuoteUpdatedEvent;
import booksandfilms.client.event.QuoteUpdatedEventHandler;
import booksandfilms.client.event.ReturnQuoteMapEvent;
import booksandfilms.client.event.ReturnQuoteMapEventHandler;
import booksandfilms.client.event.ShowQuotePopupEvent;
import booksandfilms.client.event.ShowQuotePopupEventHandler;
import booksandfilms.client.helper.RPCCall;
import booksandfilms.client.view.QuoteEditView;
import booksandfilms.client.view.QuoteListView;
import booksandfilms.client.view.QuotePopupView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class BookEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getTitleDesc();
		HasValue<String> getNotes();
		HasValue<Date> getReadDate();
		HasText getAuthorName();
		HasClickHandlers getSaveButton();
		void setGenreData(Genre genres, String genre);
		String getGenreValue();
		void setRatingData(Byte rating);
		String getRatingValue();
		void setTopicData(ArrayList<Topic> topics, Long topicId);
		String getTopicValue();
		String getTopicDesc();
		Widget asWidget();
		Widget getQuotePanel();
		HasClickHandlers getTodayButton();
	}
	
	private Book book;
	private final PersistentServiceAsync persistentService;
	private final QueryServiceAsync queryService;
	private final SimpleEventBus eventBus;
	private final Display display;
	private Author currentAuthor;
	private Genre genres;
	private QuoteListPresenter quoteListPresenter; 
	private QuoteEditPresenter quoteEditPresenter; 
	private Map<Long, Media> quoteSubsetMap = new HashMap<Long, Media>();
	private Date today_date = new Date();
	
	public BookEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Author currentAuthor, Map<Long, Media> topicsMap) {
		this.persistentService = persistentService;
		this.queryService = queryService;
		this.eventBus = eventBus;
		this.display = display;
		this.book = new Book();
		this.currentAuthor = currentAuthor;
		bind();
	}
	
	public BookEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Author currentAuthor, final Long id, final Map<Long, Media> topicsMap) {

		this(queryService, persistentService, eventBus, display, currentAuthor, topicsMap);

		if (id == null) {
			this.display.setGenreData(genres, null);
			this.display.setRatingData(null);
			Date today_date = new Date();
			this.display.getReadDate().setValue(today_date);
			this.display.getAuthorName().setText(currentAuthor.getName());
			this.display.setTopicData(Topic.sortTopics(topicsMap), null);
			return;
		} 
		
		new RPCCall<Book>() {
			@Override
			protected void callService(AsyncCallback<Book> cb) {
				queryService.getBookById(id, cb);
			}

			@Override
			public void onSuccess(Book result) {
				book = result;
				BookEditPresenter.this.display.getTitleDesc().setValue(book.getTitle());
				BookEditPresenter.this.display.setGenreData(genres, book.getGenre());
				BookEditPresenter.this.display.getNotes().setValue(book.getNotes());
				BookEditPresenter.this.display.setRatingData(book.getRating());
				BookEditPresenter.this.display.getReadDate().setValue(book.getReadDate());
				BookEditPresenter.this.display.getAuthorName().setText(book.getAuthorName());
				BookEditPresenter.this.display.setTopicData(Topic.sortTopics(topicsMap), book.getTopicId());
				getQuoteMap();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving friend...");
			}
		}.retry(3);

	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new BookEditCancelledEvent());
			}
		});
		    
		this.display.getTodayButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				BookEditPresenter.this.display.getReadDate().setValue(today_date);
			}
		});

		// Listen to events
		eventBus.addHandler(QuoteEditEvent.TYPE, new QuoteEditEventHandler() {
	    	@Override public void onEditQuote(QuoteEditEvent event) {
	            quoteEditPresenter = new QuoteEditPresenter(queryService, persistentService, eventBus, new QuoteEditView(), event.getId(), book);
	            quoteEditPresenter.go((HasWidgets) display.getQuotePanel());
	    	}
	    });

		eventBus.addHandler(QuoteAddEvent.TYPE, new QuoteAddEventHandler() {
	    	@Override public void onAddQuote(QuoteAddEvent event) {
	            quoteEditPresenter = new QuoteEditPresenter(queryService, persistentService, eventBus, new QuoteEditView(), null, book);
	            quoteEditPresenter.go((HasWidgets) display.getQuotePanel());
	    	}
	    });

		eventBus.addHandler(ShowQuotePopupEvent.TYPE, new ShowQuotePopupEventHandler() {
			public void onShowQuotePopup(ShowQuotePopupEvent event) {
				QuotePopupPresenter quotePopupPresenter = new QuotePopupPresenter(persistentService, eventBus,
						new QuotePopupView(event.getQuote().getQuoteText(), event.getClickPoint()), event.getQuote());
				quotePopupPresenter.go();
			}
		});

		eventBus.addHandler(QuoteEditCancelledEvent.TYPE, new QuoteEditCancelledEventHandler() {
	    	@Override public void onEditCancelQuote(QuoteEditCancelledEvent event) {
	    		displayQuoteList();
	    	}
	    });

		eventBus.addHandler(QuoteUpdatedEvent.TYPE, new QuoteUpdatedEventHandler() {
	    	@Override public void onUpdateQuote(QuoteUpdatedEvent event) {
	    		quoteSubsetMap.put(event.getQuote().getId(), event.getQuote());
	    		displayQuoteList();
	    	}
	    });

		eventBus.addHandler(QuoteDeleteEvent.TYPE, new QuoteDeleteEventHandler() {
			public void onDeleteQuote(QuoteDeleteEvent event) {
				quoteSubsetMap.remove(event.getQuote().getId());
	    		displayQuoteList();
			}
		});

		eventBus.addHandler(ReturnQuoteMapEvent.TYPE, new ReturnQuoteMapEventHandler() {
	    	public void onReturnQuoteMap(ReturnQuoteMapEvent event) {
	    		quoteSubsetMap = event.getQuotesMap();
	    		displayQuoteList();
	    	}
	    });

	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		book.setTitle(display.getTitleDesc().getValue().trim());
		book.setGenre(display.getGenreValue());
		book.setNotes(display.getNotes().getValue().trim());
		book.setRating(Byte.parseByte(display.getRatingValue()));
		book.setReadDate(display.getReadDate().getValue());
		if (display.getTopicValue() != null) {
			book.setTopicId(Long.parseLong(display.getTopicValue()));
			book.setTopicDesc(display.getTopicDesc());
		} else {
			book.setTopicId(null);
		}
		if (currentAuthor != null) {
			book.setAuthorId(currentAuthor.getId());
			book.setAuthorName(currentAuthor.getName());
		}

		new RPCCall<Book>() {
			@Override
			protected void callService(AsyncCallback<Book> cb) {
				persistentService.updateBook(book, cb);
			}

			public void onSuccess(Book result) {
				eventBus.fireEvent(new BookUpdatedEvent(result));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving book...");
			}

		}.retry(3);
	}
	
	private void getQuoteMap () {
		eventBus.fireEvent(new GetQuoteMapEvent(book));
	}
	
	private void displayQuoteList() {
        quoteListPresenter = new QuoteListPresenter(this.queryService, eventBus, new QuoteListView(), quoteSubsetMap, book);
        quoteListPresenter.go((HasWidgets) display.getQuotePanel());
	}
	
}
