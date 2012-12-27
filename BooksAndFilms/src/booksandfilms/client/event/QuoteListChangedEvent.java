package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Quote;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteListChangedEvent extends	GwtEvent<QuoteListChangedEventHandler> {
	public static Type<QuoteListChangedEventHandler> TYPE = new Type<QuoteListChangedEventHandler>();
	private final List<Quote> quotes;
	private final List<Quote> someQuotes;

	public QuoteListChangedEvent (List<Quote> list, List<Quote> all) {
		this.someQuotes = list;
		this.quotes = all;
	}
	
	public List<Quote> getAllQuotes(){ return quotes; }
	public List<Quote> getSelectQuotes() { return someQuotes; }
	
	@Override
	public Type<QuoteListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteListChangedEventHandler handler) {
		handler.onChangeQuoteList(this);
	}

}
