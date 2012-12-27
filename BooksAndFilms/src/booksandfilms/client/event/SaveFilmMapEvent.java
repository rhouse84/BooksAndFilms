package booksandfilms.client.event;

import java.util.Map;

import booksandfilms.client.entities.Media;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveFilmMapEvent extends GwtEvent<SaveFilmMapEventHandler>	implements EventHandler {
	public static Type<SaveFilmMapEventHandler> TYPE = new Type<SaveFilmMapEventHandler>();
	private Map<Long, Media> filmsMap;
	private Map<Long, Media> directorsMap;
	
	public SaveFilmMapEvent(Map<Long, Media> filmsMap, Map<Long, Media> directorsMap) {
		this.filmsMap = filmsMap;
		this.directorsMap = directorsMap;
	}

	public Map<Long, Media> getFilmsMap() {
		return filmsMap;
	}
	
	public Map<Long, Media> getDirectorsMap() {
		return directorsMap;
	}
	
	@Override
	public Type<SaveFilmMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveFilmMapEventHandler handler) {
		handler.onSaveFilmMap(this);
	}

}
