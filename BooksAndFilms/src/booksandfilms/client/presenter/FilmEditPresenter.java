package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.allen_sauer.gwt.log.client.Log;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.event.FilmEditCancelledEvent;
import booksandfilms.client.event.FilmUpdatedEvent;
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

public class FilmEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getTitleDesc();
		HasValue<String> getNotes();
		HasValue<String> getStars();
		HasValue<Date> getWatchDate();
		HasValue<String> getYear();
		HasText getDirectorName();
		HasClickHandlers getSaveButton();
		void setRatingData(Byte rating);
		String getRatingValue();
		void setTopicData(ArrayList<Topic> topics, Long topicId);
		String getTopicValue();
		String getTopicDesc();
		Widget asWidget();
		Widget getQuotePanel();
		HasClickHandlers getTodayButton();
	}
	
	private Film film;
	private final PersistentServiceAsync persistentService;
	private final QueryServiceAsync queryService;
	private final SimpleEventBus eventBus;
	private final Display display;
	private Director currentDirector;
	private QuoteListPresenter quoteListPresenter; 
	private QuoteEditPresenter quoteEditPresenter; 
	private Map<Long, Media> quoteSubsetMap = new HashMap<Long, Media>();
	private Date today_date = new Date();
	
	public FilmEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Director currentDirector, Map<Long, Media> topicsMap) {
		this.persistentService = persistentService;
		this.queryService = queryService;
		this.eventBus = eventBus;
		this.display = display;
		this.film = new Film();
		this.currentDirector = currentDirector;
		bind();
	}
	
	public FilmEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Director currentDirector, final Long id, final Map<Long, Media> topicsMap) {

		this(queryService, persistentService, eventBus, display, currentDirector, topicsMap);

		if (id == null) {
			this.display.setRatingData(null);
			Date today_date = new Date();
			this.display.getWatchDate().setValue(today_date);
			this.display.getDirectorName().setText(currentDirector.getName());
			this.display.setTopicData(Topic.sortTopics(topicsMap), null);
			return;
		}
	      
		new RPCCall<Film>() {
			@Override
			protected void callService(AsyncCallback<Film> cb) {
				queryService.getFilmById(id, cb);
			}

			@Override
			public void onSuccess(Film result) {
				film = result;
				FilmEditPresenter.this.display.getTitleDesc().setValue(film.getTitle());
				FilmEditPresenter.this.display.getNotes().setValue(film.getNotes());
				FilmEditPresenter.this.display.getStars().setValue(film.getStars());
				FilmEditPresenter.this.display.getYear().setValue(Integer.toString(film.getYear()));
				FilmEditPresenter.this.display.setRatingData(film.getRating());
				FilmEditPresenter.this.display.getWatchDate().setValue(film.getWatchDate());
				FilmEditPresenter.this.display.getDirectorName().setText(film.getDirectorName());
				FilmEditPresenter.this.display.setTopicData(Topic.sortTopics(topicsMap), film.getTopicId());
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
				Log.debug("FilmEditPresenter: Firing FilmEditCancelledEvent");
				eventBus.fireEvent(new FilmEditCancelledEvent());
			}
		});
		    
		this.display.getTodayButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilmEditPresenter.this.display.getWatchDate().setValue(today_date);
			}
		});

		// Listen to events
		eventBus.addHandler(QuoteEditEvent.TYPE, new QuoteEditEventHandler() {
	    	@Override public void onEditQuote(QuoteEditEvent event) {
	            quoteEditPresenter = new QuoteEditPresenter(queryService, persistentService, eventBus, new QuoteEditView(), event.getId(), film);
	            quoteEditPresenter.go((HasWidgets) display.getQuotePanel());
	    	}
	    });

		eventBus.addHandler(QuoteAddEvent.TYPE, new QuoteAddEventHandler() {
	    	@Override public void onAddQuote(QuoteAddEvent event) {
	            quoteEditPresenter = new QuoteEditPresenter(queryService, persistentService, eventBus, new QuoteEditView(), null, film);
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
	    	@Override public void onReturnQuoteMap(ReturnQuoteMapEvent event) {
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
		film.setTitle(display.getTitleDesc().getValue().trim());
		film.setNotes(display.getNotes().getValue().trim());
		film.setStars(display.getStars().getValue().trim());
		film.setYear(Integer.parseInt(display.getYear().getValue()));
		film.setRating(Byte.parseByte(display.getRatingValue()));
		film.setWatchDate(display.getWatchDate().getValue());
		if (display.getTopicValue() != null) {
			film.setTopicId(Long.parseLong(display.getTopicValue()));
			film.setTopicDesc(display.getTopicDesc());
		} else {
			film.setTopicId(null);
		}
		if (currentDirector != null) {
			film.setDirectorId(currentDirector.getId());
			film.setDirectorName(currentDirector.getName());
		}

		new RPCCall<Film>() {
			@Override
			protected void callService(AsyncCallback<Film> cb) {
				persistentService.updateFilm(film, cb);
			}

			public void onSuccess(Film result) {
				eventBus.fireEvent(new FilmUpdatedEvent(result));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving film...");
			}

		}.retry(3);
	}
	
	private void getQuoteMap () {
		eventBus.fireEvent(new GetQuoteMapEvent(film));
	}
	
	private void displayQuoteList() {
        quoteListPresenter = new QuoteListPresenter(this.queryService, eventBus, new QuoteListView(), quoteSubsetMap, film);
        quoteListPresenter.go((HasWidgets) display.getQuotePanel());
	}
	
}
