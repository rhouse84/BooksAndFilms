package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Media;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveBookMapEvent extends GwtEvent<SaveBookMapEventHandler>	implements EventHandler {
	public static Type<SaveBookMapEventHandler> TYPE = new Type<SaveBookMapEventHandler>();
	private Map<Long, Media> booksMap;
	private Map<Long, Media> authorsMap;
	private Map<Long, Media> topicsMap;
	
	public SaveBookMapEvent(Map<Long, Media> booksMap, Map<Long, Media> authorsMap, Map<Long, Media> topicsMap) {
		this.booksMap = booksMap;
		this.authorsMap = authorsMap;
		this.topicsMap = topicsMap;
	}

	public Map<Long, Media> getBooksMap() {
		return booksMap;
	}
	
	public Map<Long, Media> getAuthorsMap() {
		return authorsMap;
	}
	
	public Map<Long, Media> getTopicsMap() {
		return topicsMap;
	}
	
	@Override
	public Type<SaveBookMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveBookMapEventHandler handler) {
		handler.onSaveBookMap(this);
	}

}
