package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorEditCancelledEventHandler extends EventHandler {
	void onEditCancelAuthor(AuthorEditCancelledEvent event);
}
