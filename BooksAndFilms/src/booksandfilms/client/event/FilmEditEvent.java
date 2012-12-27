package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FilmEditEvent extends GwtEvent<FilmEditEventHandler> implements EventHandler {
	public static Type<FilmEditEventHandler> TYPE = new Type<FilmEditEventHandler>();
	private final Long id;

	public FilmEditEvent(Long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public Type<FilmEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmEditEventHandler handler) {
		handler.onEditFilm(this);
	}

}
