package booksandfilms.client.event;

import booksandfilms.client.entities.Director;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAddFilmEvent extends GwtEvent<ShowAddFilmEventHandler> {
	public static Type<ShowAddFilmEventHandler> TYPE = new Type<ShowAddFilmEventHandler>();
	private final Director director;
	
	public ShowAddFilmEvent(Director director) {
		this.director = director;
	}

	public Director getDirector() {
		return director;
	}

	@Override
	public Type<ShowAddFilmEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowAddFilmEventHandler handler) {
		handler.onShowAddFilm(this);
	}

}
