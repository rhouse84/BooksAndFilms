package booksandfilms.client.event;

import booksandfilms.client.entities.Quote;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteUpdatedEvent extends GwtEvent<QuoteUpdatedEventHandler> {
	public static Type<QuoteUpdatedEventHandler> TYPE = new Type<QuoteUpdatedEventHandler>();
	private final Quote quote;
	
	public QuoteUpdatedEvent(Quote quote) {
		this.quote = quote;
	}
	
	public Quote getQuote() {
		return quote;
	}

	@Override
	public Type<QuoteUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteUpdatedEventHandler handler) {
		handler.onUpdateQuote(this);
	}

}
