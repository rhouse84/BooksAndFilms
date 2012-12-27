package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorAddEventHandler extends EventHandler {
	void onAddAuthor(AuthorAddEvent event);
}
