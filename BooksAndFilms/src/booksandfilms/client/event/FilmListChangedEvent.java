package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Film;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FilmListChangedEvent extends GwtEvent<FilmListChangedEventHandler>	implements EventHandler {
	public static Type<FilmListChangedEventHandler> TYPE = new Type<FilmListChangedEventHandler>();
	private final List<Film> films;

	public FilmListChangedEvent (List<Film> all) {
		this.films = all;
	}
	
	public List<Film> getAllFilms(){ return films; }
	
	@Override
	public Type<FilmListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmListChangedEventHandler handler) {
		handler.onChangeFilmList(this);
	}

}
