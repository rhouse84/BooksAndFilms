package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FilmListChangedEventHandler extends EventHandler {
	void onChangeFilmList(FilmListChangedEvent event);
}
