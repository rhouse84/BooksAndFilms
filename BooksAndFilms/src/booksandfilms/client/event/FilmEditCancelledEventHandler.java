package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmEditCancelledEventHandler extends EventHandler {
	void onEditCancelFilm(FilmEditCancelledEvent event);
}
