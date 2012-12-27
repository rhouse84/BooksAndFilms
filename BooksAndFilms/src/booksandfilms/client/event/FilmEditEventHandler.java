package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmEditEventHandler extends EventHandler {
	void onEditFilm(FilmEditEvent event);
}
