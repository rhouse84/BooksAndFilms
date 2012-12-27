package booksandfilms.client.event;

import booksandfilms.client.entities.Quote;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.GwtEvent;

public class ShowQuotePopupEvent extends GwtEvent<ShowQuotePopupEventHandler> {
	public static Type<ShowQuotePopupEventHandler> TYPE = new Type<ShowQuotePopupEventHandler>();
	private final Quote quote;
	private final ClickPoint point;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowQuotePopupEvent(Quote quote, ClickPoint point) {
		this.quote = quote;
		this.point = point;
	}

	public Quote getQuote() {
		return quote;
	}

	@Override
	public Type<ShowQuotePopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowQuotePopupEventHandler handler) {
		handler.onShowQuotePopup(this);
	}

}
