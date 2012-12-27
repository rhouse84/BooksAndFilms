package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmDeleteEventHandler extends EventHandler {
	void onDeleteFilm(FilmDeleteEvent event);
}
