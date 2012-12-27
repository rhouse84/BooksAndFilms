package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FilmEditCancelledEvent extends	GwtEvent<FilmEditCancelledEventHandler> implements EventHandler {
	public static Type<FilmEditCancelledEventHandler> TYPE = new Type<FilmEditCancelledEventHandler>();

	@Override
	public Type<FilmEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmEditCancelledEventHandler handler) {
		handler.onEditCancelFilm(this);
	}

}
