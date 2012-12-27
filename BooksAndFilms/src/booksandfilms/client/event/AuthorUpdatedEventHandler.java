package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorUpdatedEventHandler extends EventHandler {
	void onUpdateAuthor(AuthorUpdatedEvent event);
}
