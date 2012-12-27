package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class QuoteEditEvent extends GwtEvent<QuoteEditEventHandler> {
	public static Type<QuoteEditEventHandler> TYPE = new Type<QuoteEditEventHandler>();
	private final Long id;
	
	public QuoteEditEvent(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	@Override
	public Type<QuoteEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(QuoteEditEventHandler handler) {
		handler.onEditQuote(this);
	}

}
