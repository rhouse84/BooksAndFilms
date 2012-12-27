package booksandfilms.client.event;

import booksandfilms.client.entities.Film;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FilmUpdatedEvent extends GwtEvent<FilmUpdatedEventHandler>	implements EventHandler {
	public static Type<FilmUpdatedEventHandler> TYPE = new Type<FilmUpdatedEventHandler>();
	private final Film film;
	
	public FilmUpdatedEvent(Film film) {
		this.film = film;
	}
	
	public Film getFilm() {
		return film;
	}


	@Override
	public Type<FilmUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmUpdatedEventHandler handler) {
		handler.onUpdateFilm(this);
	}

}
