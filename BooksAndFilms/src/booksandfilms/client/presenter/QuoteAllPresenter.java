package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.entities.QuoteAll;
import booksandfilms.client.event.QuoteAddEvent;
import booksandfilms.client.event.QuoteDeleteEvent;
import booksandfilms.client.event.QuoteDeleteEventHandler;
import booksandfilms.client.event.QuoteUpdatedEvent;
import booksandfilms.client.event.QuoteUpdatedEventHandler;
import booksandfilms.client.event.SaveQuoteMapEvent;
import booksandfilms.client.event.SetScrollHeightEvent;
import booksandfilms.client.event.SetScrollHeightEventHandler;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class QuoteAllPresenter implements Presenter {

	private Map<Long, Media> quotesMap = new HashMap<Long, Media>();
	private List<QuoteAll> quotes;
//	private List<QuoteAll> someQuotes = new ArrayList<QuoteAll>();
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		//HasKeyPressHandlers getSearchBox();
		//String getSearchValue();
		void setData(List<QuoteAll> quoteNames);
		int getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		Widget asWidget();
		void setScrollHeight(int parentHeight);
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	private Media type;
	
	public QuoteAllPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Media> quotesMap, Media type) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.quotesMap = quotesMap;
		this.type = type;
		bind();
	}
	
	public void bind() {
		if (type != null) {
			display.getAddButton().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new QuoteAddEvent(type));
				}
			});
		}
		
//		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
//			public void onKeyPress(KeyPressEvent event) {
//				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
//					searchBoxChange();
//				}
//			}
//		});
		
		// Listen to events
		eventBus.addHandler(QuoteUpdatedEvent.TYPE, new QuoteUpdatedEventHandler() {
	    	@Override public void onUpdateQuote(QuoteUpdatedEvent event) {
	    		quotesMap.put(event.getQuote().getId(), event.getQuote());
				filterQuotes();
	    	}
	    });

		eventBus.addHandler(QuoteDeleteEvent.TYPE, new QuoteDeleteEventHandler() {
			@Override public void onDeleteQuote(QuoteDeleteEvent event) {
				quotesMap.remove(event.getQuote().getId());
				filterQuotes();
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
		// If both quotesMap and type are null, we are getting all quotes, hit datastore.
		// If type has value then the quotesMap may be null.
		if (quotesMap == null && type == null) {
			fetchQuoteData();
		} else if (quotesMap != null) {
			filterQuotes();
		} else {
			display.setData(null);
		}
	}

	private void fetchQuoteData() {
		new RPCCall<Map<Long, Media>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Media>> cb) {
				rpcService.getQuoteData(cb);
			}
			
			@Override public void onSuccess(Map<Long, Media> result) {
				quotesMap = result;
				eventBus.fireEvent(new SaveQuoteMapEvent(quotesMap));
				filterQuotes();
				display.setScrollHeight(BooksAndFilms.get().getMainPanel().getOffsetHeight());
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching quotes: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void filterQuotes() {
		quotes = sortResults(quotesMap);
		display.setData(quotes);
	}
	
//	private void searchBoxChange() {
//		String searchString = display.getSearchValue();
//		someQuotes.clear();
//		if (searchString.isEmpty()) {
//			display.setData(quotes);
//		} else {
//			for (QuoteAll a : quotes) {
//				if (a.getQuoteText().toUpperCase().startsWith(searchString.toUpperCase()))
//					someQuotes.add(a);
//			}
//			display.setData(someQuotes);
//		}
//	}

	private ArrayList<QuoteAll> sortResults(Map<Long, Media> unSorted) {
		ArrayList<QuoteAll> sorted = new ArrayList<QuoteAll>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Quote quote = (Quote) thisEntry.getValue();
			QuoteAll quoteAll = new QuoteAll();
			quoteAll.setId(quote.getId());
			quoteAll.setCharacterName(quote.getCharacterName());
			quoteAll.setQuoteText(quote.getQuoteText());
			if (quote.getBookId() != null) {
				quoteAll.setMediaId(quote.getBookId());
				quoteAll.setTitle(quote.getBookTitle());
				quoteAll.setType("book");
			} else {
				quoteAll.setMediaId(quote.getFilmId());
				quoteAll.setTitle(quote.getFilmTitle());
				quoteAll.setType("film");
			}
			sorted.add(quoteAll);
		}
		Comparator<QuoteAll> cp = QuoteAll.getComparator(QuoteAll.SortParameter.MEDIA_ID_ASCENDING, QuoteAll.SortParameter.ID_ASCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
}
