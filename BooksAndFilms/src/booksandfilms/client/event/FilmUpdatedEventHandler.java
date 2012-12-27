package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmUpdatedEventHandler extends EventHandler {
	void onUpdateFilm(FilmUpdatedEvent event);
}
