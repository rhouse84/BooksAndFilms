package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.event.QuoteAddEvent;
import booksandfilms.client.event.SaveQuoteMapEvent;
import booksandfilms.client.event.ShowQuotePopupEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class QuoteListPresenter implements Presenter {

	private Map<Long, Media> quotesMap = new HashMap<Long, Media>();
	private List<Quote> quotes;
	private List<Quote> someQuotes = new ArrayList<Quote>();
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		void setData(List<Quote> quoteNames);
		int getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		Widget asWidget();
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	private Media type;
	
	public QuoteListPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Media> quotesMap, Media type) {
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
		
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchBoxChange();
				}
			}
		});
		
		if (display.getList() != null)
			display.getList().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int selectedPropertyButtonRow = display.getClickedRow(event);
					if (selectedPropertyButtonRow >= 0) {
			            ClickPoint point = display.getClickedPoint(event);
			            Quote quote = quotes.get(selectedPropertyButtonRow);
			            eventBus.fireEvent(new ShowQuotePopupEvent(quote, point));
					} else {
						//Do nothing.
					}
				}
			});
		// Listen to events

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		// If both quotesMap and type are null, we are getting all quotes, hit datastore.
		// If type has value then the quotesMap may be null.
		if (quotesMap == null && type == null) {
			Log.debug("Quotes Map and type are null, therefore hit the Datastore");
			fetchQuoteData();
		} else if (quotesMap != null) {
			filterQuotes();
		} else {
			display.setData(null);
		}
	}

	public void setQuoteSummary(List<Quote> quotes) {
		this.quotes = quotes;
	}
	
	public Quote getQuoteSummary(int index) {
		return quotes.get(index);
	}
	
	private void fetchQuoteData() {
		new RPCCall<Map<Long, Media>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Media>> cb) {
				rpcService.getQuoteData(cb);
			}
			
			@Override public void onSuccess(Map<Long, Media> result) {
				quotesMap = result;
				quotes = sortResults(quotesMap);
				eventBus.fireEvent(new SaveQuoteMapEvent(quotesMap));
				filterQuotes();
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
	
	private void searchBoxChange() {
		String searchString = display.getSearchValue();
		someQuotes.clear();
		if (searchString.isEmpty()) {
			display.setData(quotes);
		} else {
			for (Quote a : quotes) {
				if (a.getQuoteText().toUpperCase().startsWith(searchString.toUpperCase()))
					someQuotes.add(a);
			}
			display.setData(someQuotes);
		}
	}

	private ArrayList<Quote> sortResults(Map<Long, Media> unSorted) {
		ArrayList<Quote> sorted = new ArrayList<Quote>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Quote quote = (Quote) thisEntry.getValue();
			sorted.add(quote);
		}
		if (type == null) {
			Comparator<Quote> cp = Quote.getComparator(Quote.SortParameter.ID_ASCENDING);
			Collections.sort(sorted, cp);
		} else {
			if (type instanceof Book) {
				Comparator<Quote> cp = Quote.getComparator(Quote.SortParameter.BOOK_TITLE_ASCENDING);
				Collections.sort(sorted, cp);
			} else {
				Comparator<Quote> cp = Quote.getComparator(Quote.SortParameter.FILM_TITLE_ASCENDING);
				Collections.sort(sorted, cp);
			}
		}
		
		return sorted;
	}
	
}
