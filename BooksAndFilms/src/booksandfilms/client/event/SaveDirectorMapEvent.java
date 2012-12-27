package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Director;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveDirectorMapEvent extends GwtEvent<SaveDirectorMapEventHandler>	implements EventHandler {
	public static Type<SaveDirectorMapEventHandler> TYPE = new Type<SaveDirectorMapEventHandler>();
	private Map<Long, Director> directorsMap;
	
	public SaveDirectorMapEvent(Map<Long, Director> directorsMap) {
		this.directorsMap = directorsMap;
	}

	public Map<Long, Director> getDirectorsMap() {
		return directorsMap;
	}
	
	@Override
	public Type<SaveDirectorMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveDirectorMapEventHandler handler) {
		handler.onSaveDirectorMap(this);
	}

}
