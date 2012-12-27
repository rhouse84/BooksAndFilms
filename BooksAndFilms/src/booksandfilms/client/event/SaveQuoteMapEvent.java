package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Media;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveQuoteMapEvent extends GwtEvent<SaveQuoteMapEventHandler>	implements EventHandler {
	public static Type<SaveQuoteMapEventHandler> TYPE = new Type<SaveQuoteMapEventHandler>();
	private Map<Long, Media> quotesMap;
	
	public SaveQuoteMapEvent(Map<Long, Media> quotesMap) {
		this.quotesMap = quotesMap;
	}

	public Map<Long, Media> getQuotesMap() {
		return quotesMap;
	}
	
	@Override
	public Type<SaveQuoteMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveQuoteMapEventHandler handler) {
		handler.onSaveQuoteMap(this);
	}

}
