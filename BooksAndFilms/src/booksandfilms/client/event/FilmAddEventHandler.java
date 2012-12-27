package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmAddEventHandler extends EventHandler {
	void onAddFilm(FilmAddEvent event);
}
