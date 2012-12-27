package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Media;

import com.google.gwt.event.shared.GwtEvent;

public class ReturnQuoteMapEvent extends GwtEvent<ReturnQuoteMapEventHandler> {
	public static Type<ReturnQuoteMapEventHandler> TYPE = new Type<ReturnQuoteMapEventHandler>();
	private Map<Long, Media> quotesMap;

	public ReturnQuoteMapEvent(Map<Long, Media> quotesMap) {
		this.quotesMap = quotesMap;
	}
	
	public Map<Long, Media> getQuotesMap() {
		return quotesMap;
	}

	@Override
	public Type<ReturnQuoteMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReturnQuoteMapEventHandler handler) {
		handler.onReturnQuoteMap(this);
	}

}
