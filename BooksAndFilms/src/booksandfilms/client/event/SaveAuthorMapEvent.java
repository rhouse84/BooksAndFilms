package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Author;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveAuthorMapEvent extends GwtEvent<SaveAuthorMapEventHandler>	implements EventHandler {
	public static Type<SaveAuthorMapEventHandler> TYPE = new Type<SaveAuthorMapEventHandler>();
	private Map<Long, Author> authorsMap;
	
	public SaveAuthorMapEvent(Map<Long, Author> authorsMap) {
		this.authorsMap = authorsMap;
	}

	public Map<Long, Author> getAuthorsMap() {
		return authorsMap;
	}
	
	@Override
	public Type<SaveAuthorMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveAuthorMapEventHandler handler) {
		handler.onSaveAuthorMap(this);
	}

}
