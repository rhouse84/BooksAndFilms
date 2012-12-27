package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookAddEventHandler extends EventHandler {
	void onAddBook(BookAddEvent event);
}
