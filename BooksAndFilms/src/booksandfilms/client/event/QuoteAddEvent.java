package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteAddEvent extends GwtEvent<QuoteAddEventHandler> {
	public static Type<QuoteAddEventHandler> TYPE = new Type<QuoteAddEventHandler>();
	private final Object type;
	
	public QuoteAddEvent(Object type) {
		this.type = type;
	}
	
	public Object getType() {
		return type;
	}


	@Override
	public Type<QuoteAddEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteAddEventHandler handler) {
		handler.onAddQuote(this);
	}

}
