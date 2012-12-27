package booksandfilms.client.event;

import booksandfilms.client.entities.Quote;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteDeleteEvent extends GwtEvent<QuoteDeleteEventHandler> {
	public static Type<QuoteDeleteEventHandler> TYPE = new Type<QuoteDeleteEventHandler>();
	private final Quote quote;

	public QuoteDeleteEvent(Quote quote) {
		this.quote = quote;
	}
	
	public Quote getQuote() {
		return quote;
	}
	
	@Override
	public Type<QuoteDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteDeleteEventHandler handler) {
		handler.onDeleteQuote(this);	
	}

}
