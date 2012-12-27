package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteEditCancelledEvent extends GwtEvent<QuoteEditCancelledEventHandler> {
	public static Type<QuoteEditCancelledEventHandler> TYPE = new Type<QuoteEditCancelledEventHandler>();

	@Override
	public Type<QuoteEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteEditCancelledEventHandler handler) {
		handler.onEditCancelQuote(this);
	}

}
