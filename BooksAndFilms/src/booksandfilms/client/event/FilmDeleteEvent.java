package booksandfilms.client.event;

import booksandfilms.client.entities.Film;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FilmDeleteEvent extends GwtEvent<FilmDeleteEventHandler> implements EventHandler {
	public static Type<FilmDeleteEventHandler> TYPE = new Type<FilmDeleteEventHandler>();
	private final Film film;
	
	public FilmDeleteEvent(Film film) {
		this.film = film;
	}
	
	public Film getFilm() {
		return film;
	}

	@Override
	public Type<FilmDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmDeleteEventHandler handler) {
		handler.onDeleteFilm(this);	
	}

}
