package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorEditEventHandler extends EventHandler {
	void onEditAuthor(AuthorEditEvent event);
}
