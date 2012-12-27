package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorDeleteEventHandler extends EventHandler {
	void onDeleteAuthor(AuthorDeleteEvent event);
}
